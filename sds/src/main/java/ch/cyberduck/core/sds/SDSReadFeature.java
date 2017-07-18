package ch.cyberduck.core.sds;

/*
 * Copyright (c) 2002-2017 iterate GmbH. All rights reserved.
 * https://cyberduck.io/
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */

import ch.cyberduck.core.ConnectionCallback;
import ch.cyberduck.core.DefaultIOExceptionMappingService;
import ch.cyberduck.core.DisabledListProgressListener;
import ch.cyberduck.core.PasswordCallback;
import ch.cyberduck.core.Path;
import ch.cyberduck.core.exception.AccessDeniedException;
import ch.cyberduck.core.exception.BackgroundException;
import ch.cyberduck.core.features.Read;
import ch.cyberduck.core.http.HttpRange;
import ch.cyberduck.core.sds.io.swagger.client.ApiException;
import ch.cyberduck.core.sds.io.swagger.client.model.FileKey;
import ch.cyberduck.core.sds.swagger.ExtendedNodesApi;
import ch.cyberduck.core.sds.triplecrypt.CryptoInputStream;
import ch.cyberduck.core.transfer.TransferStatus;

import java.io.IOException;
import java.io.InputStream;

import eu.ssp_europe.sds.crypto.Crypto;
import eu.ssp_europe.sds.crypto.CryptoException;
import eu.ssp_europe.sds.crypto.CryptoUtils;
import eu.ssp_europe.sds.crypto.model.EncryptedFileKey;
import eu.ssp_europe.sds.crypto.model.PlainFileKey;
import eu.ssp_europe.sds.crypto.model.UserPrivateKey;

public class SDSReadFeature implements Read {

    private final SDSSession session;

    public SDSReadFeature(final SDSSession session) {
        this.session = session;
    }

    @Override
    public InputStream read(final Path file, final TransferStatus status, final ConnectionCallback connectionCallback, final PasswordCallback passwordCallback) throws BackgroundException {
        try {
            final String header;
            if(status.isAppend()) {
                final HttpRange range = HttpRange.withStatus(status);
                if(-1 == range.getEnd()) {
                    header = String.format("bytes=%d-", range.getStart());
                }
                else {
                    header = String.format("bytes=%d-%d", range.getStart(), range.getEnd());
                }
            }
            else {
                header = null;
            }
            final InputStream in = new ExtendedNodesApi(session.getClient()).getFileData(session.getToken(),
                    Long.parseLong(new SDSNodeIdProvider(session).getFileid(file, new DisabledListProgressListener())),
                    header, true);
            if(file.getType().contains(Path.Type.encrypted)) {
                final FileKey key = new ExtendedNodesApi(session.getClient()).getUserFileKey(session.getToken(),
                        Long.parseLong(new SDSNodeIdProvider(session).getFileid(file, new DisabledListProgressListener())));
                final UserPrivateKey privateKey = new UserPrivateKey();
                privateKey.setPrivateKey(session.getKeys().getPrivateKeyContainer().getPrivateKey());
                privateKey.setVersion(session.getKeys().getPrivateKeyContainer().getVersion());
                // TODO PasswordCallback
                final PlainFileKey plainFileKey = Crypto.decryptFileKey(convert(key), privateKey,
                        "ahbic3Ae");
                return new CryptoInputStream(in, Crypto.createFileDecryptionCipher(plainFileKey),
                        CryptoUtils.stringToByteArray(plainFileKey.getTag()), status.getLength() + status.getOffset());
            }
            return in;
        }
        catch(ApiException e) {
            throw new SDSExceptionMappingService().map("Download {0} failed", e, file);
        }
        catch(CryptoException e) {
            throw new AccessDeniedException(e.getMessage(), e);
        }
        catch(IOException e) {
            throw new DefaultIOExceptionMappingService().map(e);
        }
    }

    @Override
    public boolean offset(final Path file) throws BackgroundException {
        return true;
    }

    private static EncryptedFileKey convert(final FileKey k) {
        final EncryptedFileKey key = new EncryptedFileKey();
        key.setIv(k.getIv());
        key.setKey(k.getKey());
        key.setTag(k.getTag());
        key.setVersion(k.getVersion());
        return key;
    }
}

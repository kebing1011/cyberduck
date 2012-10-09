﻿// 
// Copyright (c) 2010-2012 Yves Langisch. All rights reserved.
// http://cyberduck.ch/
// 
// This program is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
// 
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
// 
// Bug fixes, suggestions and comments should be sent to:
// yves@cyberduck.ch
// 

using System.Collections.Generic;
using BrightIdeasSoftware;
using Ch.Cyberduck.Ui.Winforms.Controls;
using ch.cyberduck.core;

namespace Ch.Cyberduck.Ui.Controller
{
    public interface ITransferPromptView : IView
    {
        string Title { set; }
        TransferAction SelectedAction { get; set; }
        Path SelectedPath { get; set; }
        string LocalFileUrl { set; }
        string LocalFileSize { set; }
        string LocalFileModificationDate { set; }
        string RemoteFileUrl { set; }
        string RemoteFileSize { set; }
        string RemoteFileModificationDate { set; }
        bool DetailsVisible { set; get; }
        int NumberOfFiles { get; }

        CheckStateGetterDelegate ModelCheckStateGetter { set; }
        CheckStatePutterDelegate ModelCheckStateSetter { set; }

        TreeListView.CanExpandGetterDelegate ModelCanExpandDelegate { set; }
        TreeListView.ChildrenGetterDelegate ModelChildrenGetterDelegate { set; }

        TypedColumn<Path>.TypedImageGetterDelegate ModelIconGetter { set; }
        TypedColumn<Path>.TypedAspectGetterDelegate ModelFilenameGetter { set; }
        TypedColumn<Path>.TypedAspectGetterDelegate ModelSizeGetter { set; }
        AspectToStringConverterDelegate ModelSizeAsStringGetter { set; }
        TypedColumn<Path>.TypedImageGetterDelegate ModelWarningGetter { set; }
        TypedColumn<Path>.TypedImageGetterDelegate ModelCreateGetter { set; }
        TypedColumn<Path>.TypedImageGetterDelegate ModelSyncGetter { set; }
        MulticolorTreeListView.ActiveGetterDelegate ModelActiveGetter { set; }
        string StatusLabel { set; }
        void SetModel(IEnumerable<Path> model);
        void RefreshBrowserObject(Path path);
        void PopulateActions(IDictionary<TransferAction, string> actions);

        event VoidHandler ChangedActionEvent;
        event VoidHandler ChangedSelectionEvent;
        event VoidHandler ToggleDetailsEvent;

        //todo might be pulled out into a separate interface. same for toggledetails.
        void StartActivityAnimation();
        void StopActivityAnimation();

        // mainly used to detect a change of displayed items to update the status label
        event VoidHandler ItemsChanged;
    }
}
/*
 * DRACOON API
 * REST Web Services for DRACOON<br>built at: 2020-10-14 12:14:23<br><br>This page provides an overview of all available and documented DRACOON APIs, which are grouped by tags.<br>Each tag provides a collection of APIs that are intended for a specific area of the DRACOON.<br><br><a title='Developer Information' href='https://developer.dracoon.com'>Developer Information</a>&emsp;&emsp;<a title='Get SDKs on GitHub' href='https://github.com/dracoon'>Get SDKs on GitHub</a><br><br><a title='Terms of service' href='https://www.dracoon.com/terms/general-terms-and-conditions/'>Terms of service</a>
 *
 * OpenAPI spec version: 4.24.0-SNAPSHOT
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package ch.cyberduck.core.sds.io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
/**
 * Request model for deleting Upload Shares
 */
@Schema(description = "Request model for deleting Upload Shares")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2020-10-15T15:35:23.522373+02:00[Europe/Zurich]")
public class DeleteUploadSharesRequest {
  @JsonProperty("shareIds")
  private List<Long> shareIds = new ArrayList<>();

  public DeleteUploadSharesRequest shareIds(List<Long> shareIds) {
    this.shareIds = shareIds;
    return this;
  }

  public DeleteUploadSharesRequest addShareIdsItem(Long shareIdsItem) {
    this.shareIds.add(shareIdsItem);
    return this;
  }

   /**
   * List of share IDs
   * @return shareIds
  **/
  @Schema(required = true, description = "List of share IDs")
  public List<Long> getShareIds() {
    return shareIds;
  }

  public void setShareIds(List<Long> shareIds) {
    this.shareIds = shareIds;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeleteUploadSharesRequest deleteUploadSharesRequest = (DeleteUploadSharesRequest) o;
    return Objects.equals(this.shareIds, deleteUploadSharesRequest.shareIds);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shareIds);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeleteUploadSharesRequest {\n");
    
    sb.append("    shareIds: ").append(toIndentedString(shareIds)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
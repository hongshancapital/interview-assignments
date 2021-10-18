package com.jerry.domainservice.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.jerry.domainservice.api.model.DomainDto;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Response of invoking get domain information
 */
@Schema(description = "Response of invoking get domain information")
@Validated


public class GetResponseDto   {
  @JsonProperty("result")
  private Boolean result = null;

  @JsonProperty("message")
  private String message = null;

  @JsonProperty("data")
  private DomainDto data = null;

  public GetResponseDto result(Boolean result) {
    this.result = result;
    return this;
  }

  /**
   * Result of the service
   * @return result
   **/
  @Schema(required = true, description = "Result of the service")
      @NotNull

    public Boolean isResult() {
    return result;
  }

  public void setResult(Boolean result) {
    this.result = result;
  }

  public GetResponseDto message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Error message of the service.
   * @return message
   **/
  @Schema(description = "Error message of the service.")
  
    public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public GetResponseDto data(DomainDto data) {
    this.data = data;
    return this;
  }

  /**
   * Get data
   * @return data
   **/
  @Schema(description = "")
  
    @Valid
    public DomainDto getData() {
    return data;
  }

  public void setData(DomainDto data) {
    this.data = data;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GetResponseDto getResponseDto = (GetResponseDto) o;
    return Objects.equals(this.result, getResponseDto.result) &&
        Objects.equals(this.message, getResponseDto.message) &&
        Objects.equals(this.data, getResponseDto.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(result, message, data);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GetResponseDto {\n");
    
    sb.append("    result: ").append(toIndentedString(result)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    data: ").append(toIndentedString(data)).append("\n");
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

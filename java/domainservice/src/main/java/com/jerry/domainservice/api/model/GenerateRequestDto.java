package com.jerry.domainservice.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Request of generating the short domain information.
 */
@Schema(description = "Request of generating the short domain information.")
@Validated


public class GenerateRequestDto   {
  @JsonProperty("domainName")
  private String domainName = null;

  public GenerateRequestDto domainName(String domainName) {
    this.domainName = domainName;
    return this;
  }

  /**
   * Domain name. The maximum length of domain name is 255.
   * @return domainName
   **/
  @Schema(required = true, description = "Domain name. The maximum length of domain name is 255.")
      @NotNull

  @Size(min=1,max=255)   public String getDomainName() {
    return domainName;
  }

  public void setDomainName(String domainName) {
    this.domainName = domainName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GenerateRequestDto generateRequestDto = (GenerateRequestDto) o;
    return Objects.equals(this.domainName, generateRequestDto.domainName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(domainName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GenerateRequestDto {\n");
    
    sb.append("    domainName: ").append(toIndentedString(domainName)).append("\n");
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

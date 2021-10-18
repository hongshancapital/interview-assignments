package com.jerry.domainservice.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Domain dto
 */
@Schema(description = "Domain dto")
@Validated


public class DomainDto   {
  @JsonProperty("domainName")
  private String domainName = null;

  public DomainDto domainName(String domainName) {
    this.domainName = domainName;
    return this;
  }

  /**
   * domainName
   * @return domainName
   **/
  @Schema(required = true, description = "domainName")
      @NotNull

    public String getDomainName() {
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
    DomainDto domainDto = (DomainDto) o;
    return Objects.equals(this.domainName, domainDto.domainName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(domainName);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DomainDto {\n");
    
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

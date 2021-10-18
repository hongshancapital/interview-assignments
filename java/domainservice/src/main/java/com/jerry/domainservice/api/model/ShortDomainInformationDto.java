package com.jerry.domainservice.api.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * ShortDomainInformationDto
 */
@Validated


public class ShortDomainInformationDto   {
  @JsonProperty("shortDomainInformation")
  private String shortDomainInformation = null;

  public ShortDomainInformationDto shortDomainInformation(String shortDomainInformation) {
    this.shortDomainInformation = shortDomainInformation;
    return this;
  }

  /**
   * Short domain information, it's a 8 lenght character and unique for each long domain,
   * @return shortDomainInformation
   **/
  @Schema(required = true, description = "Short domain information, it's a 8 lenght character and unique for each long domain,")
      @NotNull

  @Size(min=8,max=8)   public String getShortDomainInformation() {
    return shortDomainInformation;
  }

  public void setShortDomainInformation(String shortDomainInformation) {
    this.shortDomainInformation = shortDomainInformation;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ShortDomainInformationDto shortDomainInformationDto = (ShortDomainInformationDto) o;
    return Objects.equals(this.shortDomainInformation, shortDomainInformationDto.shortDomainInformation);
  }

  @Override
  public int hashCode() {
    return Objects.hash(shortDomainInformation);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ShortDomainInformationDto {\n");
    
    sb.append("    shortDomainInformation: ").append(toIndentedString(shortDomainInformation)).append("\n");
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

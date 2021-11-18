package red.lilu.service.api;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShortUrlControllerTests {

  @Autowired
  private MockMvc mockMvc;

  /**
   * 缓存生成的短网址
   */
  private static String su = "";

  @Test
  @Order(1)
  public void home() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/")
        )
        .andExpect(
            MockMvcResultMatchers.status().isOk()
        )
        .andDo(
            MockMvcResultHandlers.print()
        )
        .andReturn();
  }

  @Test
  @Order(2)
  public void to() throws Exception {
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/to")
                .param("url", "https://lilu.red/app/ne/")
        )
        .andExpect(
            MockMvcResultMatchers.status().isOk()
        )
        .andDo(
            MockMvcResultHandlers.print()
        )
        .andReturn();

    su = result.getResponse().getContentAsString();
    System.out.println(su);
  }

  @Test
  @Order(3)
  public void from() throws Exception {
    MvcResult result = mockMvc.perform(
            MockMvcRequestBuilders.get("/from")
                .param("su", su)
        )
        .andExpect(
            MockMvcResultMatchers.status().isOk()
        )
        .andDo(
            MockMvcResultHandlers.print()
        )
        .andReturn();

    System.out.println(
        result.getResponse().getContentAsString()
    );
  }

  @Test
  @Order(4)
  public void fromError() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/from")
                .param("su", "")
        )
        .andExpect(
            MockMvcResultMatchers.status().isBadRequest()
        )
        .andDo(
            MockMvcResultHandlers.print()
        )
        .andReturn();
  }

}

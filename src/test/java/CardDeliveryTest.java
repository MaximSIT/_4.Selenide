import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class CardDeliveryTest {

    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    public void shouldPass() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Тюмень");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String meetingDate = generateDate(4,"dd.MM.yyy");
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван Иванович");
        $("[data-test-id='phone'] input").setValue("+71111111111");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $(".notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldBe(Condition.exactText("Встреча успешно забронирована на " + meetingDate));
    }
}
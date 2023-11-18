package com.vadimistar.weatherviewer;

import com.vadimistar.weatherviewer.dto.api.FoundLocationDto;
import com.vadimistar.weatherviewer.exceptions.OpenWeatherApiException;
import com.vadimistar.weatherviewer.services.LocationService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static com.vadimistar.weatherviewer.utils.Utils.createMockRestTemplateReturnsCode;
import static com.vadimistar.weatherviewer.utils.Utils.createMockRestTemplateReturnsJSON;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = WeatherviewerApplication.class
)
@TestPropertySource(
        locations = "classpath:application-test.properties"
)
@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationServiceTests {
    @Autowired
    LocationService locationService;

    private final static String HTTP_CLIENT_RESPONSE = "[{\"name\":\"Moscow\",\"local_names\":{\"pl\":\"Moskwa\",\"os\":\"Мæскуы\",\"he\":\"מוסקווה\",\"pt\":\"Moscou\",\"mt\":\"Moska\",\"ur\":\"ماسکو\",\"it\":\"Mosca\",\"ms\":\"Moscow\",\"ie\":\"Moskwa\",\"te\":\"మాస్కో\",\"cu\":\"Москъва\",\"sr\":\"Москва\",\"nb\":\"Moskva\",\"nn\":\"Moskva\",\"jv\":\"Moskwa\",\"cs\":\"Moskva\",\"zu\":\"IMoskwa\",\"sq\":\"Moska\",\"ro\":\"Moscova\",\"sm\":\"Moscow\",\"iu\":\"ᒨᔅᑯ\",\"mn\":\"Москва\",\"cy\":\"Moscfa\",\"vo\":\"Moskva\",\"nl\":\"Moskou\",\"hi\":\"मास्को\",\"fa\":\"مسکو\",\"za\":\"Moscow\",\"li\":\"Moskou\",\"ht\":\"Moskou\",\"co\":\"Moscù\",\"no\":\"Moskva\",\"bs\":\"Moskva\",\"ka\":\"მოსკოვი\",\"br\":\"Moskov\",\"ab\":\"Москва\",\"su\":\"Moskwa\",\"tk\":\"Moskwa\",\"av\":\"Москва\",\"da\":\"Moskva\",\"bn\":\"মস্কো\",\"ak\":\"Moscow\",\"dv\":\"މޮސްކޯ\",\"kg\":\"Moskva\",\"ku\":\"Moskow\",\"kw\":\"Moskva\",\"af\":\"Moskou\",\"ko\":\"모스크바\",\"ar\":\"موسكو\",\"mr\":\"मॉस्को\",\"mi\":\"Mohikau\",\"ja\":\"モスクワ\",\"kl\":\"Moskva\",\"es\":\"Moscú\",\"ps\":\"مسکو\",\"kn\":\"ಮಾಸ್ಕೋ\",\"lg\":\"Moosko\",\"gv\":\"Moscow\",\"wa\":\"Moscou\",\"de\":\"Moskau\",\"so\":\"Moskow\",\"ta\":\"மாஸ்கோ\",\"ss\":\"Moscow\",\"cv\":\"Мускав\",\"tl\":\"Moscow\",\"feature_name\":\"Moscow\",\"sv\":\"Moskva\",\"gl\":\"Moscova - Москва\",\"lv\":\"Maskava\",\"uz\":\"Moskva\",\"hr\":\"Moskva\",\"sw\":\"Moscow\",\"gd\":\"Moscobha\",\"my\":\"မော်စကိုမြို့\",\"am\":\"ሞስኮ\",\"bi\":\"Moskow\",\"sh\":\"Moskva\",\"id\":\"Moskwa\",\"fy\":\"Moskou\",\"sk\":\"Moskva\",\"el\":\"Μόσχα\",\"tt\":\"Мәскәү\",\"mk\":\"Москва\",\"yi\":\"מאסקווע\",\"tg\":\"Маскав\",\"en\":\"Moscow\",\"bo\":\"མོ་སི་ཁོ།\",\"oc\":\"Moscòu\",\"qu\":\"Moskwa\",\"ga\":\"Moscó\",\"ty\":\"Moscou\",\"kv\":\"Мӧскуа\",\"ln\":\"Moskú\",\"ca\":\"Moscou\",\"ia\":\"Moscova\",\"vi\":\"Mát-xcơ-va\",\"sc\":\"Mosca\",\"ru\":\"Москва\",\"tr\":\"Moskova\",\"fr\":\"Moscou\",\"lt\":\"Maskva\",\"ba\":\"Мәскәү\",\"hy\":\"Մոսկվա\",\"et\":\"Moskva\",\"sg\":\"Moscow\",\"fi\":\"Moskova\",\"sl\":\"Moskva\",\"ml\":\"മോസ്കോ\",\"mg\":\"Moskva\",\"na\":\"Moscow\",\"eu\":\"Mosku\",\"uk\":\"Москва\",\"ce\":\"Москох\",\"gn\":\"Mosku\",\"st\":\"Moscow\",\"az\":\"Moskva\",\"ay\":\"Mosku\",\"th\":\"มอสโก\",\"ch\":\"Moscow\",\"ascii\":\"Moscow\",\"bg\":\"Москва\",\"hu\":\"Moszkva\",\"zh\":\"莫斯科\",\"fo\":\"Moskva\",\"la\":\"Moscua\",\"ky\":\"Москва\",\"kk\":\"Мәскеу\",\"ug\":\"Moskwa\",\"io\":\"Moskva\",\"se\":\"Moskva\",\"is\":\"Moskva\",\"be\":\"Масква\",\"eo\":\"Moskvo\",\"yo\":\"Mọsko\",\"wo\":\"Mosku\",\"an\":\"Moscú\",\"dz\":\"མོསི་ཀོ\"},\"lat\":55.7504461,\"lon\":37.6174943,\"country\":\"RU\",\"state\":\"Moscow\"},{\"name\":\"Moscow\",\"local_names\":{\"en\":\"Moscow\",\"ru\":\"Москва\"},\"lat\":46.7323875,\"lon\":-117.0001651,\"country\":\"US\",\"state\":\"Idaho\"},{\"name\":\"Moscow\",\"lat\":45.071096,\"lon\":-69.891586,\"country\":\"US\",\"state\":\"Maine\"},{\"name\":\"Moscow\",\"lat\":35.0619984,\"lon\":-89.4039612,\"country\":\"US\",\"state\":\"Tennessee\"},{\"name\":\"Moscow\",\"lat\":39.5437014,\"lon\":-79.0050273,\"country\":\"US\",\"state\":\"Maryland\"}]";

    @Test
    public void searchLocationsOk() {
        RestTemplate restTemplate = createMockRestTemplateReturnsJSON(HTTP_CLIENT_RESPONSE);

        List<FoundLocationDto> locations = locationService.searchLocations(restTemplate, "query", 1);

        assertNotEquals(0, locations.size());

        FoundLocationDto location = locations.get(0);

        assertEquals("Moscow", location.getName());
        assertEquals(55.7504461, location.getLat());
        assertEquals(37.6174943, location.getLon());
    }

    @Test
    public void weatherError() {
        RestTemplate restTemplate = createMockRestTemplateReturnsCode(HttpStatus.FORBIDDEN);

        assertThrows(OpenWeatherApiException.class,
                () -> locationService.searchLocations(restTemplate, "query", 1));
    }
}

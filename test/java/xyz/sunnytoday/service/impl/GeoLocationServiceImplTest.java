package xyz.sunnytoday.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import xyz.sunnytoday.common.config.AppConfig;
import xyz.sunnytoday.dto.GeoLocation;
import xyz.sunnytoday.service.face.GeoLocationService;

class GeoLocationServiceImplTest {

    @Test
    @DisplayName("위치정보 탐색 테스트")
    void GeoLocationResponseTest() throws Exception {
        AppConfig.Init("test/app-config.xml"); //키값 넣고 테스트 할것

        GeoLocationService geoLocationService = new GeoLocationServiceImpl();
        GeoLocation response = null;
        try {
            response = geoLocationService.requestGeoLocationData("39.115.110.153");
        } catch (Exception e) {
            throw e;
        }

        System.out.println(response);
        AppConfig.destroy();
    }

}
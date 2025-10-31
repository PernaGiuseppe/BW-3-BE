package buildweek5.BW_3_BE.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary getImageUploader(@Value("${cloudinary.name}") String cloudName,
                                       @Value("${cloudinary.key}") String apiKey,
                                       @Value("${cloudinary.secret}") String secret){

        // Cloudinary ha bisogno di un Map per la sua configurazione
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", secret);
        return new Cloudinary(config);
    }
}

package com.example.Inves.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.net.URL;
import java.util.Date;
/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 28/11/2024 - 12:58
 */

@Data
@Builder
@AllArgsConstructor
public class MarketNews {


    private String Title;
    private String PublishedDate;
    private String Topic;
    private String Source;
    private String url;
}

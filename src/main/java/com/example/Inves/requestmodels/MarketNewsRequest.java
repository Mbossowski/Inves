package com.example.Inves.requestmodels;

import com.example.Inves.models.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 11:54
 */
@Data
public class MarketNewsRequest {
    private String Title;
    private String Content;
    private Date PublishedDate;
    private String Source;


}

package com.example.Inves.requestmodels;

import com.example.Inves.models.*;
import lombok.Data;


/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:03
 */

@Data
public class WatchlistRequest {
    private String ID_User;
    private String ID_Stock;
    private String AddedDate;
}

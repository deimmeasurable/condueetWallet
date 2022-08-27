package com.example.condueetwallet.Dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MetaData {
    private String displayName;
    private String variableName;
    private String value;

}

package com.maslov.springbootjb.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PeriodTimestamps {
    private Timestamp from;
    private Timestamp to;
}

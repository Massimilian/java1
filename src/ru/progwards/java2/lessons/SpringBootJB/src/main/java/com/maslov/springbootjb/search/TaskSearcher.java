package com.maslov.springbootjb.search;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class TaskSearcher {
    // характеристики Tasks в виде именно объектов - чтобы была возможность передать null
    String name;
    Boolean done;
    Integer categoryId;
    Integer priorityId;
    Date dateFrom;
    Date dateTo;

    // характеристики постраничности
    Integer pageNumber;
    Integer pageSize;

    // характеристики сортировок
    String column;
    String direction;
    String sortDirection;
}

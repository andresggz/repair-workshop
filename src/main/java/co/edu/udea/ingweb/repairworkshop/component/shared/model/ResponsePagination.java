package co.edu.udea.ingweb.repairworkshop.component.shared.model;

import lombok.AllArgsConstructor;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Generated
@Getter
@NoArgsConstructor
@AllArgsConstructor
public final class ResponsePagination<T> implements Serializable {

    private List<T> result;

    private long total;

    private int page;

    private int returnedRecords;

    public static <E> ResponsePagination<E> fromObject(List<E> result, long total, Integer page,
                                                       Integer returnedRecords) {
        return new ResponsePagination<>(result, total, page, returnedRecords);
    }
}

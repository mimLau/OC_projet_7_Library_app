package com.mimi.mlibrary.service.contract;

import com.mimi.mlibrary.model.dto.borrowing.BorrowingDto;
import com.mimi.mlibrary.model.entity.borrowing.Borrowing;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface BorrowingService {

    BorrowingDto findBorrowingById(int id );
    List<BorrowingDto> findAll();
    List<BorrowingDto> findByMemberId( int memberId );
    BorrowingDto save( int memberId, int copyId );
    void extendBorrowingReturnDateById( int borrowingId );
    //void  updateBorrowingExtensionValueById( int id) ;
    void  updateBorrowingStatus( int borrowingId );
    List<BorrowingDto> findByDelay();
    Map<String, LocalDate> findOutdatedBorrowingsEmailMember();

}

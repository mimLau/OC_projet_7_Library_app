package com.mimi.mlibrary.service.impl;

import com.mimi.mlibrary.mapper.loan.LoanMapper;
import com.mimi.mlibrary.model.dto.loan.LoanDto;
import com.mimi.mlibrary.model.entity.account.Member;
import com.mimi.mlibrary.model.entity.loan.Loan;
import com.mimi.mlibrary.model.entity.loan.LoanStatus;
import com.mimi.mlibrary.model.entity.publication.Copy;
import com.mimi.mlibrary.repository.account.MemberRepository;
import com.mimi.mlibrary.repository.loan.LoanRepository;
import com.mimi.mlibrary.repository.publication.CopyRepository;
import com.mimi.mlibrary.service.contract.LoanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    final static Logger logger  = LogManager.getLogger(Loan.class);

    private LoanRepository loanRepository;
    private CopyRepository copyRepository;
    private MemberRepository memberRepository;
    private LoanMapper LoanMapper;

    public LoanServiceImpl(LoanRepository loanRepository, LoanMapper LoanMapper, CopyRepository copyRepository, MemberRepository memberRepository) {
        this.loanRepository = loanRepository;
        this.LoanMapper = LoanMapper;
        this.copyRepository = copyRepository;
        this.memberRepository = memberRepository;
    }


    @Override
    public LoanDto findLoanById( int id ) {
        return   LoanMapper.toDto( loanRepository.findLoanById( id ).orElse(null));
    }

    @Override
    public List<LoanDto> findAll() {
        return LoanMapper.toDtoList( loanRepository.findAllLoans( LoanStatus.INPROGRESS));
    }

    @Override
    public List<LoanDto> findByMemberId( int memberId ) {
        return LoanMapper.toDtoList( loanRepository.findByMemberId( memberId, LoanStatus.INPROGRESS) );
    }

    /**
     *

     * @param memberId The id of the user who borrows a publication
     * @param copyId The id of the available copy of the publication which will be borrowed
     * @return The created Loan
     */
    @Override
    public LoanDto save( int memberId, int copyId ) {

        // Member research
        Optional<Member> member = memberRepository.getMemberById( memberId );
        int currentsLoans = member.get().getNbOfCurrentsLoans();

        // Copy research
        Optional <Copy> copy = copyRepository.findCopyById( copyId );
        boolean available = copy.get().isAvailable();

        /*
         * Member has right to borrow only 5 publications
         * To register or create a Loan of a copy, this one should be available.
         */
        if( currentsLoans < 5 && available == true ) {

            LocalDate today = LocalDate.now();

            copyRepository.updateCopyAvailability( false, copyId );
            copyRepository.updateCopyReturnDateById( today.plusDays( 28 ),copyId );
            memberRepository.updateNbOfCurrentsLoans( memberId, 1);


            //Set attributes to the created Loan.

            LoanDto LoanDto = new LoanDto();

            LoanDto.setCopy( copy.get() );
            LoanDto.setMember( member.get() );
            LoanDto.setReturnDate( today.plusDays( 28 ) );
            LoanDto.setLoanDate( today );
            LoanDto.setExtented( false );
            LoanDto.setLoanStatus( "INPROGRESS" );
            LoanDto.setReminderNb( 0 );

            Optional.of( LoanMapper.INSTANCE.toEntity( LoanDto ) ).ifPresent( Loan -> loanRepository.save( Loan ));
            return LoanDto;
        }

        logger.info(" L'exemplaire sélectionné n'est pas disponible et/ou l'utilisateur a 5 livres à son actif:\n"
                        + "Id utilisateur: " + member.get().getId() + "\n"
                        + "Nombre de livres empruntés: " + currentsLoans + "\n"
                        + "Publication disponible?: " + available + "\n");

        return null;
    }

    @Override
    public void extendLoanReturnDateById( int LoanId ) {

       //Retrieve a Loan by its id
        LoanDto LoanDto = LoanMapper.toDto( loanRepository.findLoanById( LoanId ).orElse(null));

        //Retrieve the return date of this Loan
        LocalDate returnDate = LoanDto.getReturnDate();

       boolean extension = LoanDto.isExtented();
       if( extension != true ) {
           //Extend the return date by 4 weeks
           returnDate = returnDate.plusDays( 28 );
           loanRepository.updateLoanReturnDateById( returnDate, LoanId );
           loanRepository.updateExtensionById( LoanId );

           int copyId = LoanDto.getCopy().getId();
           copyRepository.updateCopyReturnDateById( returnDate, copyId );
       }
        //TODO MESSAGE TO SIGNAL THAT THE USER HAS ALREADY EXTENDED ITS Loan.
    }

    @Override
    public void updateLoanStatus( int loanId ) {

        Optional<Loan> loan = loanRepository.findById( loanId );
        int copyId = loan.get().getCopy().getId();
        int memberId = loan.get().getMember().getId();

        copyRepository.updateCopyAvailability( true, copyId );
        copyRepository.updateCopyReturnDateById( null, copyId );
        memberRepository.updateNbOfCurrentsLoans( memberId, -1 );

        loanRepository.updateLoanStatus( loanId, LoanStatus.FINISHED );
    }


    @Override
    public void updateReminderNbById( int loanId ) {
        loanRepository.updateReminderNbById( loanId );
    }

    @Override
    public List<LoanDto> findByDelay() {
        LocalDate currentDate = LocalDate.now();
        return  LoanMapper.INSTANCE.toDtoList( loanRepository.findByDelay( currentDate , LoanStatus.INPROGRESS ) );

    }

    @Override
    public Map<String, LocalDate> findOutdatedLoansEmailMember() {

        List <LoanDto>  loanDtos = this.findByDelay();
        Map<String, LocalDate> emailsAndReturnDates = new HashMap<>();

        for( LoanDto loan : loanDtos )
            emailsAndReturnDates.put(loan.getMember().getAccountOwnerEmail(), loan.getReturnDate() );

        return emailsAndReturnDates;
    }
}

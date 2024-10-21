package fpt.capstone.iAccount.controller;

import fpt.capstone.iAccount.dto.response.AccountResponse;
import fpt.capstone.iAccount.service.AccountService;
import fpt.capstone.proto.account.AccountDtoProto;
import fpt.capstone.proto.account.AccountServiceGrpc;
import fpt.capstone.proto.account.GetAccountRequest;
import fpt.capstone.proto.account.GetAccountResponse;
import fpt.capstone.proto.lead.GetLeadRequest;
import fpt.capstone.proto.lead.GetLeadResponse;
import fpt.capstone.proto.lead.LeadDtoProto;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class AccountGrpcController extends AccountServiceGrpc.AccountServiceImplBase {

    @Autowired
    AccountService accountService;

    @Override
    public void getAccount (GetAccountRequest request, StreamObserver<GetAccountResponse> responseObserver){
        long accountId = request.getAccountId();
        AccountResponse accountResponse = accountService.getDetailAccount(accountId);
        try {
            GetAccountResponse getAccountResponse ;

            if(accountResponse != null){
                AccountDtoProto proto = AccountDtoProto.newBuilder()
                        .setAccountId(accountResponse.getAccountId()==null?0L:accountResponse.getAccountId())
                        .setAccountName(accountResponse.getAccountName()==null?"":accountResponse.getAccountName())
                        .setDescription(accountResponse.getDescription()==null?"":accountResponse.getDescription())
                        .setPhone(accountResponse.getPhone()==null?"":accountResponse.getPhone())
                        .setWebsite(accountResponse.getWebsite()==null?"":accountResponse.getWebsite())
                        .setNoEmployee(accountResponse.getNoEmployee()==null?0:accountResponse.getNoEmployee())
                        .setIsDeleted(accountResponse.getIsDeleted()==null?0:accountResponse.getIsDeleted())
                        .build();
                getAccountResponse = GetAccountResponse.newBuilder()
                        .setResponse(proto)
                        .build();
            }else{
                getAccountResponse = GetAccountResponse.getDefaultInstance();
            }
            responseObserver.onNext(getAccountResponse);
            responseObserver.onCompleted();
        } catch (Exception e) {
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }
}

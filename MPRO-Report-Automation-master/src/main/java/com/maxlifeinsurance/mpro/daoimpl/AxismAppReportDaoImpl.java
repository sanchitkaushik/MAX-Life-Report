package com.maxlifeinsurance.mpro.daoimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.amazonaws.services.lambda.runtime.Context;
import com.maxlifeinsurance.mpro.dao.AxismAppReportDao;
import com.maxlifeinsurance.mpro.dto.AxismApp;

public class AxismAppReportDaoImpl implements AxismAppReportDao {

	String sql = "(select distinct(T.ProposalNo),mpd.customerId as ClientId,insAgr.agentId as AgentCode,gen.creationTime,gen.planType as ProductCode,illuInsAgr.premiumPayingTerm,illuInsAgr.policyTerm,E.value as SPCode,CA.amount as ModalPremiun, mpd.branchCode as BranchCode,mpd.customerName as ProposerName,FP3.typeName as PaymentMode,mpd.AYFP as AFYP,mpd.ssnCode as SSNId,PlanName =CASE insAgr.marketingName WHEN 'Max Life LifeTime Income Solution' THEN 'Max Life Life Gain Premier' WHEN 'Max Life Spousal Retirement Solution' THEN 'Max Life Life Perfect Partner Super' WHEN 'Max Life Time Income Solution' THEN 'Max Life Life Perfect Partner Super' WHEN 'GIP' THEN 'Max Life Guaranteed Income Plan' ELSE insAgr.marketingName END,[Case status]= case tppStatus when '1' then case tppsubstatus when '3' then 'pending at payment page/OTP Page' when '4' then 'pending at document upload page' when '5' then 'discrepancy marked' else 'case successfully moved from mApp' end when '2' then 'case in TPP' when '3' then 'case in TPP' when '4' then 'case in TPP' else tppStatus end into #temp1 from (select agr.id as AgrId,agr.mliProposalData_id as mpdId,agr.identifier as ProposalNo from eappprod.eapp.Agreement agr where agr.contextId=4 and agr.statusCode in (0,3,8))T INNER JOIN eappprod.eapp.Agreement illuAgr on T.mpdId = illuAgr.mliProposalData_id and illuAgr.contextId=2 and illuAgr.statusCode in (0,3,8) INNER JOIN eappprod.eapp.InsuranceAgreement illuInsAgr on illuAgr.id=illuInsAgr.id INNER JOIN eappprod.eapp.FinancialServicesAgreement_Premium FSA_P1 with(nolock) ON AgrId=FSA_P1.FinancialServicesAgreement_id INNER JOIN eappprod.eapp.FinancialProvision FP3 with(nolock) on FP3.id=FSA_P1.agreementPremium_id INNER JOIN eappprod.eapp.FinancialServicesAgreement_Premium FSA_P with(nolock) ON illuAgr.id=FSA_P.FinancialServicesAgreement_id INNER JOIN eappprod.eapp.FinancialProvision FP2 with(nolock) on FP2.id=FSA_P.agreementPremium_id INNER JOIN eappprod.eapp.FinancialProvision_Installment FPI ON FPI.FinancialProvision_id=FP2.id INNER JOIN eappprod.eapp.Installment INS ON INS.id=FPI.basedOn_id INNER JOIN eappprod.eapp.CurrencyAmount CA with(nolock) ON CA.id=INS.netInitialPremiumAmount_id left join eappprod.eapp.InsuranceAgreement insAgr with (nolock) on insAgr.id=T.AgrId left join eappprod.eapp.MLIProposalData mpd with (nolock) on mpd.id=T.mpdId inner join eappprod.eapp.POLICY_NO_GENERATION_LOG gen with (nolock) on gen.caseId=mpd.caseId INNER JOIN eappprod.eapp.partyRoleInAgreement PRIA with(nolock) ON T.AgrId=PRIA.isPartyRoleIn_id INNER JOIN eappprod.eapp.Party P with(nolock) ON P.id=PRIA.playerParty_id INNER JOIN eappprod.eapp.party_extension PE with(nolock) ON PE.Party_id=P.id INNER JOIN eappprod.eapp.Extension E with(nolock) ON E.id=PE.includesExtension_id where T.ProposalNo is not null and E.name = 'SPCertificateNo' and DATEDIFF(DAY,gen.creationTime,GETDATE()) < 10 AND (mpd.channel IS NULL OR mpd.channel!='A') and FP3.typeName is not null) select * from #temp1 select agr.identifier as ProposalNo,ext.value from eappprod.eapp.Agreement agr join eappprod.eapp.PartyRoleInAgreement pr on agr.id=pr.isPartyRoleIn_id join eappprod.eapp.Party_Extension pet on pet.Party_id=pr.playerParty_id join eappprod.eapp.Extension ext on ext.id=pet.includesExtension_id where ext.name='customerClassification' and agr.identifier in (select distinct ProposalNo from #temp1)"
			+ ";";

	@Override
	public List getAllGridReportData(Context context) {

		EntityManagerFactory emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
		EntityManager entitymanager = emfactory.createEntityManager();
		entitymanager.getTransaction().begin();

		Query q = entitymanager.createNativeQuery(sql, AxismApp.class);
		List<AxismApp> result = q.getResultList();

		List<String> resultInStrings = new ArrayList<>(result.size());
		for (AxismApp axismApp : result) {
			resultInStrings.add(Objects.toString(axismApp, null));
		}
		/*
		 * List<String> strings = result.stream().map(object -> Objects.toString(object,
		 * null)) .collect(Collectors.toList());
		 */

		return resultInStrings;
	}

}

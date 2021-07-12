package com.ipl.processor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.ipl.data.MatchInput;
import com.ipl.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match>{
	
//	private static final Logger logger = LoggerFactory.getLogger(MatchDataProcessor.class); 
	
	@Override
	public Match process(MatchInput matchInput) throws Exception {
		Match match = new Match();
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		
		match.setId(Long.parseLong(matchInput.getId()));
		match.setCity(matchInput.getCity());
		match.setDate(LocalDate.parse(matchInput.getDate(),df));
		match.setVenue(matchInput.getVenue());
		match.setPlayerOfMatch(matchInput.getPlayer_of_match());
		
//		team1 and team2 depending on innings order
		String firstInningsTeam,secondInningsTeam;
		
		if("bat".equals(matchInput.getToss_decision())) {
			firstInningsTeam = matchInput.getToss_winner();
			secondInningsTeam = firstInningsTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
		}
		else {
			secondInningsTeam = matchInput.getToss_winner();
			firstInningsTeam = secondInningsTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
		}

		match.setTeam1(firstInningsTeam);
		match.setTeam2(secondInningsTeam);
		match.setTossWinner(matchInput.getToss_winner());
		match.setTossDecision(matchInput.getToss_decision());
		match.setResult(matchInput.getResult());
		match.setResultMargin(matchInput.getResult_margin());
		match.setUmpire1(matchInput.getUmpire1());
		match.setUmpire2(matchInput.getUmpire2());
		
		return match;
	}

}

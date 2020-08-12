
Feature: Employee sign up
  As an employee, I want to sign up for the IMS

  Scenario Outline: Sign up to IMS
  	Given IMS is running
  	Given The following employees exist
  	| firstname | lastname | email         | username  | password  | isManager |
    | Sophie    | Chijioke | sop@gmail.com | sophie		 | sophie123 | true 	   | 
    Given Employee wants to sign up with "<firstname>", "<lastname>", "<email>", "<username>", "<password>" and "<isManager>"
    When Employee initiates sign up process
    Then The sign up status is "<result>"
    
    Examples: 
      | firstname | lastname | email         | username  | password | isManager | result   |
      | Jason     | Chijioke | ali@gmail.com | jason		 | jason123 | true 	    | valid    |
      |           | Chijioke | ali@gmail.com | jason		 | jason123 | true 	    | invalid  |
      | Jason     |          | ali@gmail.com | jason		 | jason123 | false     | invalid  |
      | Jason     | Chijioke |               | jason		 | jason123 | true 	    | invalid  |
      | Jason     | Chijioke | ali@gmail.com |      		 | jason123 | true    	| invalid  |
      | Jason     | Chijioke | ali@gmail.com | jason		 |          | true    	| invalid  |
      | Jason     | Chijioke | sop@gmail.com | jason		 | jason123 | true 	    | invalid  |
      | Jason     | Chijioke | ali@gmail.com | sophie		 | jason123 | true 	    | invalid  |
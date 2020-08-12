
Feature: Employee login
  As an employee, I want to sign into IMS

  Scenario Outline: Log in to IMS
  	Given IMS has the following employees
  	 |user_name | password |
  	 |Hyacinth 	| ali123	 |
    Given Employee wants to log in with "<user_name>" and "<password>"
    When Employee initiates log in process
    Then The log in status is "<result>"
    
    Examples: 
      | user_name  | password | result  |
      | Hyacinth   |  ali123  | success |
      | Hyacinth   |  ali321  | Fail    |
      | 					 |  ali123  | Fail    |

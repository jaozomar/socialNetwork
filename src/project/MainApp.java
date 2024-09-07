/* 
Team: Byte the Bullet
Matthew Say, Victor Mendoza, Phat Ho, Joshua Ozomaro
12/10/21
Final Part 1
*/

/*
 * This program creates an interactive interface for the user to interact with the profile network.
 * A menu is created to allow the user to choose what action they want to take within the social network.
 */

package project;

import java.util.Scanner;

public class MainApp 
{
    public static void main (String[] args) 
    {
        Scanner input = new Scanner(System.in);
        String inputString;
        String updateInputString;
        int number;
        int updateNumber;
        int statusNumber;
        
        UserProfiles profileData = new UserProfiles();
        
        System.out.println("Hello! Would you like to join our Social Network?");
        System.out.print("Enter 'y' for yes or 'n' for no: ");
        inputString = input.nextLine();
        
        char letter = inputString.charAt(0);
        
        
        if (letter == 'y' || letter == 'Y') 
        {
	        do
	        {
	            mainMenu();
	            number = Integer.parseInt(input.nextLine());
	            
	            switch(number)
	            {
	                
		            case 1:
		            {
		            	
		                System.out.println("\nCREATE PROFILE:\n" 
		                        + "----------------------");
		                System.out.print("Enter a username for your new Profile: ");
		                inputString = input.nextLine();
		                
		                profileData.createProfile(inputString);
		                break;
		                
		            }
		              
		            case 2:
		            {
		            	
		                System.out.println("\nUPDATE PROFILE:\n"
		            + "----------------------");
		                System.out.print("\nEnter the username for the profile you want to update: ");
		                inputString = input.nextLine();
						if(profileData.contains(inputString)) 
		                	System.out.println("CURRENTLY UPDATING " + inputString);
		            
		                do
		                {
							updateNumber = -1;
							if(profileData.contains(inputString)) {
								updateMenu();
								updateNumber = Integer.parseInt(input.nextLine());
							}
			                
			                switch(updateNumber)
			                {
				                case 1:
				                {
				                	System.out.print("Enter the new username: ");
				                	updateInputString = input.nextLine();
				                	
				                	profileData.updateUsername(inputString, updateInputString);
				                	updateNumber = 5; //since the username is the key for the hashed dictionary, we exit the updating loop to avoid issues
				                	break;
				                }
				                
				                case 2:
				                {
				                	System.out.print("Enter the new name: ");
				                	updateInputString = input.nextLine();
				                	
				                	profileData.updateName(inputString, updateInputString);
				                	break;
				                }
				                
				                case 3:
				                {
				                	System.out.print("Enter the file path for the new image you want to use: ");
				                	updateInputString = input.nextLine();
				                	
				                	profileData.updateImage(inputString, updateInputString);
				                	break;
				                }
				                
				                case 4:
				                {
				                	System.out.println("1 Online");
				                	System.out.println("2 Offline");
				                	System.out.println("3 Busy");
				                	System.out.print("What status do you want to pick: ");
				                	statusNumber = Integer.parseInt(input.nextLine());
				                	
				                	if(statusNumber > 3 || statusNumber < 1)
				                		System.out.println("INVALID STATUS CHOICE.");
				                	else
				                		profileData.updateStatus(inputString, statusNumber);
				                	break;
				                }
				                
				                case 5:
				                	break;
				                
				                default:
				                {
				                	System.out.println("INVALID UPDATE CHOICE.");
									updateNumber = 5;
									break;
				                }
			                
			                }
		                }
		                while(updateNumber != 5);
		                
						if(profileData.contains(inputString))
		                	System.out.println("Finished updating profile.");
		                break;
		                
		            }
		            
		            case 3:
		            {
		            	
		                System.out.println("\nSEARCH PROFILES:\n"
		            + "----------------------");
		                
		                profileData.searchProfiles();
		                break;
		                
		            }
		            
		            case 4:
		            {
		                System.out.println("\nVIEW A PROFILE:\n"
		            + "-------------------");
		                
		                System.out.print("Enter the username of the profile you want to view: ");
		                inputString = input.nextLine();
		                
		                profileData.viewSingleProfile(inputString);
		                break;
		            }
		            
		            case 5:
		            {
		                System.out.println("\nVIEW ALL PROFILES:\n"
		            + "-------------------");	
		                
		                profileData.viewProfiles();
		                break;
		            }
		              
		            case 6:
		            {
		            	
		                System.out.println("\nADD A FRIEND:\n"
		            + "-------------------");
		                
		                System.out.print("Enter the username of a profile that will add a new friend: ");
		                inputString = input.nextLine();
		                
		                System.out.print("Enter the username of the new friend: ");
		                updateInputString = input.nextLine();
		                
		                profileData.addFriend(inputString, updateInputString);
		                break;
		                
		            }
		              
		            case 7:
		            {
		            	
		                System.out.println("\nREMOVE A FRIEND:\n"
		            + "----------------------");
		                
		                System.out.print("Enter the username of a profile that will remove a friend: ");
		                inputString = input.nextLine();
		                
		                System.out.print("Enter the username of the friend to be removed: ");
		                updateInputString = input.nextLine();
		                
		                profileData.removeFriend(inputString, updateInputString);
		                break;
		                
		            }
		            
		            case 8:
		            {
		            	System.out.println("\nSEARCH FRIENDS:\n"
		    		            + "----------------------");
		            	
		            	System.out.print("Enter the username of a profile in order to search their friend list: ");
		            	inputString = input.nextLine();
		            	
		            	profileData.searchFriends(inputString);
		            	break;
		            }
		              
		            case 9:
		            {
		            	
		                System.out.println("\nDISPLAY FRIEND LIST:\n"
		            + "----------------------");
		                
		                System.out.print("Enter the username of a profile in order to view their friend list: ");
		                inputString = input.nextLine();
		                
		                profileData.viewFriends(inputString);
		                break;
		                
		            }
		            
		            case 10:
		            {
		            	System.out.println("\nVIEW A FRIEND:\n"
		    		            + "----------------------");
		            	
		            	System.out.print("Enter the username of a profile in order to view one of their friends: ");
		            	inputString = input.nextLine();
		            	
		            	profileData.viewFriends(inputString);
		            	
		            	if(profileData.contains(inputString)) {
		            		System.out.print("Enter the username of the friend you want to view: ");
		            		updateInputString = input.nextLine();
		            		profileData.viewFriend(inputString, updateInputString);
		            	}
		            	
		            	break;
		            }
		              
		            case 11:
		            {
		            	System.out.println("\nDISPLAY MUTUAL FRIEND LIST:\n"
		    		            + "----------------------");
		            	
		            	System.out.print("Enter the username of a profile in order to view all of their mutual friends (friends' friends): ");
		            	inputString = input.nextLine();
		            	
		            	System.out.println();
		            	profileData.mutualFriends(inputString);
		            	break;
		            }
		            
		            case 12:
		            {
		            	System.out.println("\nDISPLAY CONNECTIONS LIST:\n"
		    		            + "----------------------");
		            	
		            	System.out.print("Enter the username of a profile in order to view all of their connections (their friends, friends' friends, friends' friends' friends, etc.): \n");
		            	inputString = input.nextLine();
		            	
		            	System.out.println();
		            	profileData.displayConnections(inputString);
		            	break;
		            }
		            
		            case 13:
		            {
		            	
		                System.out.println("\nDELETE PROFILE\n"
		            + "----------------------");
		                
		                System.out.print("Enter the username of the profile that will be deleted: ");
		                inputString = input.nextLine();
		                
		                profileData.deleteProfile(inputString);
		                break;
		            }
		            
		            case 14:
		            	break;
		            	
		            // In case the user inputs a number that is not included in the options menu, throw error message
		            default:
		            	System.out.println("\nINVALID SELECTION!\n");
	            }
	        }
	        while(number != 14);
            
        } 
        
        System.out.println("\nCome back again soon!");
        System.exit(0);
    } // end main
    
    public static void mainMenu()
    {
        System.out.println("\nWelcome to the main menu! Choose an option:\n");
        System.out.println("1 to Create a Profile");
        System.out.println("2 to Update a Profile");
        System.out.println("3 to Search Profiles");
        System.out.println("4 to View a Profile");
        System.out.println("5 to View All Profiles");
        System.out.println("6 to Add a Friend");
        System.out.println("7 to Remove a Friend");
        System.out.println("8 to Search Friend List");
        System.out.println("9 to View Friend List");
        System.out.println("10 to View a Friend's Profile");
        System.out.println("11 to View Friends' Friend List");
        System.out.println("12 to View List of Connections");
        System.out.println("13 to Delete a Profile");
        System.out.println("14 to Exit Network");
        System.out.print("Enter your selection: ");
        
    }
    
    public static void updateMenu()
    {
    	System.out.println("\n1 Username");
    	System.out.println("2 Name");
    	System.out.println("3 Image");
    	System.out.println("4 Status");
    	System.out.println("5 STOP updating this profile");
    	System.out.print("Choose an item to update: ");
    }
}
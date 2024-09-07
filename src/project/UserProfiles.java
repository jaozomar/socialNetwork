/* 
Team: Byte the Bullet
Matthew Say, Victor Mendoza, Phat Ho, Joshua Ozomaro
12/10/21
Final Part 1
*/

/*
 * This program uses a hashed dictionary to create a social network for the user to create their own
 * profile to where they have the ability to update their current status as well
 * as search for friends to add or remove from their friend list. Various methods allow the user to
 * create, read, update, and delete profiles as well as friends of these profiles. A undirected graph
 * is used to map the relationships amongst friends in the network.
 */

package project;

import java.util.Iterator;
import java.util.Scanner;

//This class is for the network of user profiles
public class UserProfiles { 
	private DictionaryInterface<String, Profile> profiles = null;
	private UndirectedGraph<String> friendGraph = null;
	
	public UserProfiles() {
		//Hashed dictionary with username as the key and a profile class obect as the value
		profiles = new HashedDictionary<>(13);
		friendGraph = new UndirectedGraph<>();
	}
	
	
	//creates a profile with a given user input and adds it to the hashed dictionary
	public void createProfile(String userInput) {
		
		userInput = userInput.toLowerCase().replaceAll("\\s", "");
		
		Profile newUser = new Profile(userInput);
		
		//Add an entry by using the input username and a Profile object
		if(profiles.contains(userInput))
			System.out.println("The username + " + userInput + " is already taken");
		else {
			profiles.add(userInput, newUser);
			friendGraph.addVertex(userInput);
		}
	}
	
	
	//deletes a profile with a given username from the hashed dictionary
	public void deleteProfile(String userInput) {
		
		userInput = userInput.toLowerCase().replaceAll("\\s", "");
		
		//Delete a profile by using the input username
		if(profiles.contains(userInput)) {
			
			//clears all friends before deleting the profile
			Iterator<Profile> valueIterator = profiles.getValueIterator();
			
			while (valueIterator.hasNext()) {
				Profile obj = valueIterator.next();
				
				if(obj.containsFriend(profiles.getValue(userInput))) 
					obj.removeFriend(profiles.getValue(userInput));
			}
			
			profiles.remove(userInput);
			
			rebuildFriendGraph();
		}
		else
			System.out.println("The username " + userInput + " does not belong to any account.");
	}
	
	
	//displays the info of every profile in the hashed dictionary
	public void viewProfiles() {

		Iterator<Profile> valueIterator = profiles.getValueIterator();

		while (valueIterator.hasNext()) {
			
			//uses the displayInfo method from Profile class
			valueIterator.next().displayInfo();
		}
		
	}
	
	
	//displays the info of a specific profile in the hashed dictionary
	public void viewSingleProfile(String userInput) {
		userInput = userInput.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(userInput)) {
			profiles.getValue(userInput).displayInfo();
			profiles.getValue(userInput).displayImage(); //Displays the user image as well
		}
		else
			System.out.println("The username " + userInput + " does not belong to any profile.");
	}
	
	
	//allows the user to search for profiles by entering a search term
	public void searchProfiles() {
		System.out.println();
		System.out.print("Search for profile username: ");
		Scanner sc = new Scanner(System.in);
		String userInput = sc.nextLine().toLowerCase().replaceAll("\\s", ""); //Removes whitespaces and makes everything lowercase
		
		Iterator<String> keyIterator   = profiles.getKeyIterator();
		Iterator<Profile> valueIterator = profiles.getValueIterator();

		boolean searchTermFound;
		int resultCount = 0;
		
		System.out.println("Profile search results for " + userInput + "...");
		
		while (keyIterator.hasNext() && valueIterator.hasNext()) {
			searchTermFound = true;
			String actualUsername = keyIterator.next();
			String username = actualUsername.toLowerCase(); //Makes a copy with everthing lowercase so it can be compared to the search term
			
			//Checks if the first couple letters match up. Instead of searching for the entire username to match results, it checks letter by letter.
			//For example, if you search up 'A', all the profiles that start with 'A' will show up as results.
			
			if(userInput.length() <= username.length()) {
				
				for(int i = 0; i < userInput.length(); i++) {
					if(username.charAt(i) != userInput.charAt(i))
						searchTermFound = false;
				}
				
			}
			else {
				
				for(int j = 0; j < username.length(); j++) {
					if(username.charAt(j) != userInput.charAt(j))
						searchTermFound = false;
				}
				
			}

			
			if(searchTermFound && (userInput.length() > 0)) {
				System.out.println(actualUsername);
				resultCount++;
			}
		}
		
		//Message if no matching profiles were found
		if(resultCount == 0) {
			System.out.println("No profile usernames matched your search term.");
		}
		
		System.out.println();
	}
	
	//checks if a profile is contained in the network
	public boolean contains(String userInput) {
		userInput = userInput.toLowerCase().replaceAll("\\s", "");
		return profiles.contains(userInput);
	}
	
	//Deletes every profile in the network.
	//This is not used in the MainApp file because it doesn't make sense in a realistic scenario.
	//This method could still be used when running tests and trying to quickly clear every profile
	public void clear() {
		profiles.clear();
		friendGraph.clear();
	}
	
	//Change the username of a given profile
	public void updateUsername(String username, String newUsername) {
		username = username.toLowerCase().replaceAll("\\s", "");
		newUsername = newUsername.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username) && !(profiles.contains(newUsername))) {
			
			profiles.getValue(username).setUsername(newUsername);
			Profile newData = profiles.getValue(username);
			
			deleteProfile(username);
			
			profiles.add(newUsername, newData);
			
			rebuildFriendGraph();
		}
		else if(profiles.contains(newUsername))
			System.out.println("The username " + newUsername + " already belongs to another profile.");
		else
			System.out.println("The username " + username + " does not belong to any profile.");
	}
	
	//Change the name of a given profile
	public void updateName(String username, String name) {
		username = username.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username))
			profiles.getValue(username).setName(name);
		else
			System.out.println("The username " + username + " does not belong to any profile.");
	}
	
	
	//Change the status of a given profile by inputting choice number
	public void updateStatus(String username, int choice) {
		username = username.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username)) {
			if(choice == 1)
				profiles.getValue(username).setOnlineStatus();
			else if(choice == 2)
				profiles.getValue(username).setOfflineStatus();
			else if(choice == 3)
				profiles.getValue(username).setBusyStatus();
		}
		else
			System.out.println("This username " + username + "does not belong to any profile.");
	}
	
	
	//Change the image of a given profile by inputting the file location
	public void updateImage(String username, String fileLocation) {
		username = username.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username))
			profiles.getValue(username).setImage(fileLocation);
		else
			System.out.println("This username " + username + "does not belong to any profile.");
	}
	
	
	//Add a friend to the given profile. Both profiles will be friends of each other
	public void addFriend(String username, String friendUsername) {
		username = username.toLowerCase().replaceAll("\\s", "");
		friendUsername = friendUsername.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username) && profiles.contains(friendUsername)) {
			
			profiles.getValue(username).addFriend(profiles.getValue(friendUsername));
			profiles.getValue(friendUsername).addFriend(profiles.getValue(username));
			friendGraph.addEdge(username, friendUsername);
		}
		else
			System.out.println("At least one of the given usernames does not exist in the network.");
	}
	
	//Remove a friend of a given profile. Both profiles will no longer be friends of each other
	public void removeFriend(String username, String friendUsername) {
		username = username.toLowerCase().replaceAll("\\s", "");
		friendUsername = friendUsername.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username) && profiles.contains(friendUsername)) {
			
			profiles.getValue(username).removeFriend(profiles.getValue(friendUsername));
			profiles.getValue(friendUsername).removeFriend(profiles.getValue(username));
			
			rebuildFriendGraph();
		}
		else
			System.out.println("at least one of the given usernames does not exist in the network.");		
	}
	
	
	//View all friends of a given profile
	public void viewFriends(String username) {
		username = username.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username))
			profiles.getValue(username).viewProfiles();
		else
			System.out.println("The username " + username + " does not belong to any profile.");
	}
	
	//View one friend of a given profile
	public void viewFriend(String username, String friendUsername) {
		username = username.toLowerCase().replaceAll("\\s", "");
		friendUsername = friendUsername.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username) && profiles.contains(friendUsername))
			profiles.getValue(username).viewProfile(friendUsername);
		else
			System.out.println("At least one of the given usernames does not exist in the network.");	
	}
	
	//Search friends of a given profile
	public void searchFriends(String username) {
		username = username.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(username)) {
			System.out.println();
			System.out.print("Enter a username to search in " + username + "'s friends: ");
			Scanner sc = new Scanner(System.in);
			String userInput = sc.nextLine().toLowerCase().replaceAll("\\s", ""); //Removes whitespaces and makes everything lowercase
			profiles.getValue(username).searchFriends(userInput);
		}
		else
			System.out.println("The username " + username + " does not belong to any profile.");
	
	}
	
	//builds the friends graph by looking over the hash map
	protected void rebuildFriendGraph() {
		friendGraph.clear(); //clears the graph and will re build it with the new changes
		
		Iterator<String> keyIterator   = profiles.getKeyIterator();
		
		while (keyIterator.hasNext()) {
			friendGraph.addVertex(keyIterator.next());  //adds all usernames to graph
		}
		
		keyIterator = profiles.getKeyIterator(); //resets keyIterator
		
		while(keyIterator.hasNext()) { //loops again to check for friend relationships
			String currentName = keyIterator.next();
			ListInterface<Profile> friends = profiles.getValue(currentName).getFriends();
			
			for(int i = 1; i <= friends.getLength(); ++i) {
				if(!(friendGraph.hasEdge(currentName, friends.getEntry(i).getUsername()))) //if not already done, add an edge to display the friend relationship
					friendGraph.addEdge(currentName, friends.getEntry(i).getUsername());
			}
		}
	}
	
	/** Displays each profile's information and friends. */
	public void displayConnections(String startPoint)
	{
		startPoint = startPoint.toLowerCase().replaceAll("\\s", "");
		
		if(profiles.contains(startPoint)) {
			QueueInterface<String> p1Queue = new LinkedQueue<>();
			p1Queue = friendGraph.getBreadthFirstTraversal(startPoint);
		      
		    System.out.println(startPoint + "'s Connections: ");
		    System.out.println("------------------------------");
		      
		    p1Queue.dequeue(); //don't print out the startPoint, they are not a connection to themselves
		    while (!p1Queue.isEmpty())
		    {
		       String p = p1Queue.dequeue();
		       profiles.getValue(p).simpleDisplay();
		       System.out.println();
		    } 
		}
		else
			System.out.println("The username " + startPoint + " does not belong to any profile.");
	}
	
	 /** Displays the given profile's mutual friends. */
    public void mutualFriends(String startPoint) {
    	startPoint = startPoint.toLowerCase().replaceAll("\\s", "");
    	
    	if(profiles.contains(startPoint)) {
		    QueueInterface<String> p1Queue = friendGraph.getBreadthFirstTraversal(startPoint);
		    StackInterface<String> myEmptyStack = new LinkedStack<>();
		    
			System.out.println(startPoint + "'s Mutual Friends: ");
			System.out.println("------------------------------");
		       
		       
		    while (!p1Queue.isEmpty()) {
		    	String p = p1Queue.dequeue();
		           
		        //check if the shortest path between both profiles is 2. If it is 2, then they are mutual friends
		         if (friendGraph.getShortestPath(startPoint, p, myEmptyStack) == 2) { 
			         System.out.println(p);
		         }
		    }
		    
		    System.out.println();
    	}
		else
			System.out.println("The username " + startPoint + " does not belong to any profile.");
    }
    
}


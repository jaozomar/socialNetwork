/* 
Team: Byte the Bullet
Matthew Say, Victor Mendoza, Phat Ho, Joshua Ozomaro
12/10/21
Final Part 1
*/

/*
 * This program is a class that holds all the information of a user profile.
 * Users are able to edit the user name, name, image, status, and friend list of the profile.
 * The friend list is created with an array list that can add, remove, read, and search friends.
 */

package project;

import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;

public class Profile {
	private ListInterface<Profile> friends = new AList<>();
	private String username;
	private String name = "Guest";
	private Image image = null;
	private String status = "Online";
	
	public Profile() {
		//Generates a random username if one is not given. However, we never really need to do this in the hashed dictionary.
		username = "user#" + Integer.toString((int)(Math.random()));
	}
	
	public Profile(String chosenUsername) {
		username = chosenUsername.toLowerCase().replaceAll("\\s", ""); //Gets rid of white spaces
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setImage(String imageFileLocation) {
		//reads image from input file location, catches exception if image can't be read
		try {
			image = ImageIO.read(new File(imageFileLocation));
		} catch (IOException e) {
			System.out.println("The image could not be read.");
		}
	}
	
	
	public void displayImage() {
		//Creates a frame to display the profile's image if it has one
		if(image != null) {
			JLabel img = new JLabel(new ImageIcon(image));
			JFrame frame = new JFrame(username);
			frame.setVisible(true);
			frame.setSize(300,400);
			frame.add(img);
		}
		else
			System.out.println(username + " does not have a Profile Picture.");
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setOnlineStatus() {
		status = "Online";
	}
	
	public void setOfflineStatus() {
		this.status = "Offline";
	}
	
	public void setBusyStatus() {
		this.status = "Busy";
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username.toLowerCase().replaceAll("\\s", "");
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void displayInfo() {
		System.out.println("---------------");
		System.out.println("Username: " + getUsername());
		System.out.println("Name: " + getName());
		System.out.println("Currently: " + getStatus());
		System.out.println("---------------");
		viewProfiles();
	}
	
	public void addFriend(Profile friend) {
		if(!(friends.contains(friend)))
				friends.add(friend);
		else
			System.out.println("These accounts are already friends.");
	}
	
	public void removeFriend(Profile friend) {
		for(int i = 1; i <= friends.getLength(); i++) 
		{
			if(friends.getEntry(i) == friend) {
				friends.remove(i);
				break;
			}
		}
	}
	
	
	public boolean containsFriend(Profile friend) {
		return friends.contains(friend);
	}
	
	//views all friends' profiles
	public void viewProfiles() {
		System.out.println("List of " + username + "'s Friends: ");
		
		for(int i = 1; i <= friends.getLength(); ++i) {
			System.out.print(friends.getEntry(i).getUsername() + " ");
		}
		
		System.out.println();
	}
	
	//views one specific friend's profile
	public void viewProfile(String userInput) {
		for(int i = 1; i <= friends.getLength(); ++i) {
			if((friends.getEntry(i).getUsername()).equals(userInput))
				friends.getEntry(i).displayInfo();
				friends.getEntry(i).displayImage();
		}
	}
	
	public void searchFriends(String userInput) {
		System.out.println("Friend search results for " + userInput + "...");
		
		boolean searchTermFound;
		int resultCount = 0;
		
		for(int i = 1; i <= friends.getLength(); i++)
		{
			searchTermFound = true;
			String friendUser = friends.getEntry(i).username.toLowerCase();
			
			//compares search term with friend usernames letter by letter
			if(userInput.length() <= friendUser.length()) {
	
				for(int j = 0; j < userInput.length(); j++) {
					if(friendUser.charAt(j) != userInput.charAt(j))
						searchTermFound = false;
				}
				
			}
			else {
				
				for(int k = 0; k < friendUser.length(); k++) {
					if(friendUser.charAt(k) != userInput.charAt(k))
						searchTermFound = false;
				}	
				
			}
			

			if(searchTermFound && (userInput.length() > 0)) {
				System.out.println(friends.getEntry(i).username);
				resultCount++;
			}
			
		}
		
		if(resultCount == 0) {
			System.out.println("No profile usernames matched your search term.");
		}
		
		System.out.println();
	}
	
	public ListInterface<Profile> getFriends() {
		return friends;
	}
	
	public void simpleDisplay()
	{
		System.out.println(username);
		viewProfiles();
	} 
}

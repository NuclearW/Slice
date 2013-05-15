package com.nuclearw.slice;

import java.util.HashSet;
import java.util.Set;

public class Slice {
	// TODO: Make this class represent a slice, a slice should know it's id number, its owner, its successor, its managers, its members, and its exits.
	// It will be responsible for checking if a person may enter a slice. It is also responsible for creating a new exit when it has no exits.

	private final int id;

	private String owner;
	private String successor = null;
	private Set<String> managers = new HashSet<String>();
	private Set<String> members = new HashSet<String>();

	private Set<SliceExitPortal> exits = new HashSet<SliceExitPortal>();;

	public Slice(int id, String owner) {
		this.id = id;
		this.owner = owner;
	}

	public final int getId() {
		return id;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner){
		this.owner = owner;
	}

	public boolean hasSuccessor() {
		return successor != null;
	}

	public String getSuccessor() {
		return (successor == null) ? null : successor; 
	}

	public void setSuccessor(String successor) {
		if(!managers.contains(successor)) {
			addManager(successor);
		}
		this.successor = successor;
	}

	public boolean isManager(String manager) {
		return managers.contains(manager);
	}

	public void addManager(String manager) {
		if(!members.contains(manager)) {
			addMember(manager);
		}
		managers.add(manager);
	}

	public void removeManager(String manager) {
		managers.remove(manager);

		if(successor.equalsIgnoreCase(manager)) {
			successor = null;
		}
	}

	public Set<String> getManagers() {
		return new HashSet<String>(managers);
	}

	public boolean isMember(String member) {
		return members.contains(member);
	}

	public void addMember(String member) {
		members.add(member);
	}

	public void removeMember(String member) {
		members.remove(member);
		removeManager(member);
	}

	public Set<String> getMembers() {
		return new HashSet<String>(members);
	}

	public int exitCount() {
		return exits.size();
	}
}

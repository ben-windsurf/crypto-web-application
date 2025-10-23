---
description: Break down a task into 1-2 story point tickets on Ben's Team Jira board with Epic organization
auto_execution_mode: 3
---

# Jira Task Breakdown Workflow

This workflow takes a task description and breaks it down into 1-2 story point tickets on Ben's Team Jira board, organized under a unified Epic.

## Prerequisites

- Access to Ben's Team Jira project (BT)
- Atlassian MCP server configured and authenticated
- Task description provided by user

## Workflow Steps

### 1. Analyze Task Description
- Review the provided task description
- Identify the main components and deliverables
- Determine logical breakdown into 1-2 tickets
- Estimate story points for each component (1, 2, 3, 5, 8, 13)

### 2. Create Epic
Create a unified Epic to organize the tickets:
```
Epic Summary: [Task Name] - [Brief Description]
Epic Description: 
- Overview of the complete task
- Business value and objectives
- Success criteria
- Dependencies and assumptions
```

### 3. Create Story/Task Tickets
For each identified component, create a ticket with:

**Ticket 1:**
- **Type**: Story or Task (based on user-facing vs technical work)
- **Summary**: Clear, actionable title
- **Story Points**: 1-8 points based on complexity
- **Description**: 
  - Background and context
  - Detailed requirements
  - Technical approach (if applicable)
- **Acceptance Criteria**:
  - Specific, testable conditions
  - Definition of done
  - Quality gates

**Ticket 2 (if needed):**
- Same structure as Ticket 1
- Ensure logical separation from Ticket 1
- Consider dependencies between tickets

### 4. Link Tickets to Epic
- Associate both tickets with the created Epic
- Set proper Epic Link relationships

### 5. Validation
- Verify all tickets are created in Ben's Team (BT) project
- Confirm Epic contains both tickets
- Review story point estimates (total should be reasonable for the task scope)
- Ensure acceptance criteria are clear and testable

## Story Point Guidelines

- **1 Point**: Very simple, well-understood task (< 2 hours)
- **2 Points**: Simple task with minimal complexity (2-4 hours)
- **3 Points**: Moderate complexity, some unknowns (4-8 hours)
- **5 Points**: Complex task, multiple components (1-2 days)
- **8 Points**: Very complex, significant effort (2-3 days)
- **13 Points**: Should be broken down further

## Ticket Types

- **Story**: User-facing features or functionality
- **Task**: Technical work, infrastructure, or internal improvements
- **Bug**: Defect fixes (if applicable)

## Example Breakdown

**Input**: "Implement user authentication system"

**Epic**: User Authentication System
- Secure login/logout functionality
- Session management
- Password reset capability

**Ticket 1**: Implement Login/Logout API (5 points)
- Backend authentication endpoints
- JWT token generation
- Session management
- AC: Users can login with valid credentials, receive JWT token, logout clears session

**Ticket 2**: Build Authentication UI Components (3 points)
- Login form with validation
- Logout button
- Password reset form
- AC: Responsive forms, proper error handling, accessibility compliant

## Notes

- Always consider security implications for crypto applications
- Include proper error handling and validation
- Consider mobile responsiveness
- Plan for testing requirements
- Document API changes if applicable
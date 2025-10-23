---
description: Resolve merge conflicts and push an updated PR
auto_execution_mode: 3
---

# PR Merge Conflict Resolution Workflow

## Overview
This workflow guides Cascade through resolving merge conflicts in Pull Requests by leveraging the current local repository state and pushing updated versions back to the PR branch.

## Prerequisites
- Local repository is up-to-date with the main/master branch
- Git is configured with appropriate credentials
- Access to the PR link and repository permissions

## Workflow Steps

### 1. Initial Assessment
```bash
# Check current repository status
git status
git branch -a
git log --oneline -10
```

**Cascade Actions:**
- Verify we're in the correct repository directory
- Identify current branch and any uncommitted changes
- Note the project structure (crypto-web-application with frontend + Java backend)

### 2. Extract PR Information
From the provided PR link, extract:
- **Repository URL**: `https://github.com/username/repo-name`
- **PR Number**: Extract from URL pattern `/pull/123`
- **Source Branch**: Usually shown in PR interface
- **Target Branch**: Usually `main` or `master`

**Cascade Command Pattern:**
```bash
# Example: https://github.com/benlehrburger/crypto-web-application/pull/45
# Extract: repo=benlehrburger/crypto-web-application, pr=45
```

### 3. Fetch PR Branch
```bash
# Method 1: If you have write access to the original repo
git fetch origin pull/{PR_NUMBER}/head:{LOCAL_BRANCH_NAME}
git checkout {LOCAL_BRANCH_NAME}

# Method 2: If working with a fork
git remote add fork {FORK_URL}
git fetch fork {SOURCE_BRANCH}
git checkout -b pr-{PR_NUMBER} fork/{SOURCE_BRANCH}
```

**Cascade Variables:**
- `{PR_NUMBER}`: Extracted PR number
- `{LOCAL_BRANCH_NAME}`: Use format `pr-{PR_NUMBER}-conflicts`
- `{SOURCE_BRANCH}`: Branch name from PR (e.g., `feature/new-trading-ui`)

### 4. Identify Merge Conflicts
```bash
# Attempt merge with target branch to identify conflicts
git merge origin/main
# or
git merge origin/master
```

**Expected Output Analysis:**
- Look for `CONFLICT` messages
- Note conflicted file paths
- Identify conflict types (content, rename, delete/modify)

### 5. Analyze Conflict Context
For each conflicted file, examine:

```bash
# View conflict markers
git status
git diff --name-only --diff-filter=U
```

**Cascade Analysis Pattern:**
```markdown
## Conflict Analysis
- **File**: `path/to/conflicted/file.js`
- **Conflict Type**: Content conflict
- **Local Changes**: [Brief description]
- **Incoming Changes**: [Brief description]
- **Resolution Strategy**: [Merge both/Keep local/Keep incoming/Custom merge]
```

### 6. Resolve Conflicts by File Type

#### Frontend Files (JS/HTML/CSS)
```bash
# For crypto-web-application specific files
# Priority: Maintain functionality of trading dashboard, charts, and API integrations
```

**Resolution Priority:**
1. **Critical**: API endpoints, trading logic, security features
2. **High**: UI components, chart configurations, data processing
3. **Medium**: Styling, documentation, test files
4. **Low**: Comments, formatting, non-functional changes

#### Backend Files (Java/Maven)
```bash
# For backend/ directory conflicts
# Priority: Database models, REST endpoints, business logic
```

**Java-Specific Considerations:**
- Maintain Spring Boot compatibility (currently 2.7.15)
- Preserve JPA entity relationships
- Keep REST controller mappings consistent
- Maintain Maven dependency versions

#### Configuration Files
```bash
# Handle pom.xml, package.json, config files carefully
# These often have the most complex conflicts
```

**Configuration Priority:**
1. **pom.xml**: Java version, Spring Boot version, dependencies
2. **package.json**: Scripts, dependencies, Jest configuration
3. **Playwright/Jest configs**: Test configurations
4. **.github/workflows**: CI/CD pipeline integrity

### 7. Conflict Resolution Commands

#### Manual Resolution
```bash
# Open conflicted file in editor
# Look for conflict markers: <<<<<<<, =======, >>>>>>>
# Edit file to resolve conflicts
# Remove conflict markers
git add {resolved_file}
```

#### Automated Resolution Strategies
```bash
# Keep local version (current repository state)
git checkout --ours {file_path}

# Keep incoming version (PR changes)
git checkout --theirs {file_path}

# For binary files or complete rewrites
git rm {file_path}  # if removing
git add {file_path}  # if keeping incoming version
```

### 8. Validation After Resolution

#### Build and Test Validation
```bash
# Frontend validation
npm install
npm run lint
npm run test
npm run build

# Backend validation (if applicable)
cd backend/
mvn clean compile
mvn test
cd ..

# Integration testing
npm run test:coverage
npx playwright test
```

**Cascade Validation Checklist:**
- [ ] No remaining conflict markers in any files
- [ ] All builds pass without errors
- [ ] Critical functionality tests pass
- [ ] No new linting errors introduced
- [ ] Dependencies resolve correctly

### 9. Commit Resolution
```bash
# Stage all resolved files
git add .

# Commit with descriptive message
git commit -m "Resolve merge conflicts in PR #{PR_NUMBER}

- Resolved conflicts in: {list_of_files}
- Maintained compatibility with current codebase
- Preserved {critical_features}
- All tests passing"
```

### 10. Push Updated PR

#### Method 1: Direct Push (if you have write access)
```bash
# Push to the original PR branch
git push origin {SOURCE_BRANCH}
```

#### Method 2: Force Push (if working on forked branch)
```bash
# Push to your fork
git push fork {SOURCE_BRANCH} --force-with-lease
```

#### Method 3: New Branch (safest approach)
```bash
# Create new branch with resolved conflicts
git checkout -b {SOURCE_BRANCH}-resolved
git push origin {SOURCE_BRANCH}-resolved

# Update PR to point to new branch (manual step in GitHub UI)
```

### 11. Post-Resolution Verification

```bash
# Verify the push was successful
git log --oneline -5
git status

# Check remote branch status
git ls-remote origin {SOURCE_BRANCH}
```

**Final Cascade Actions:**
1. Confirm PR shows "No conflicts" status
2. Verify all CI/CD checks pass
3. Document resolution approach in PR comments
4. Clean up local branches if needed

## Cascade-Specific Optimization Notes

### Error Handling
- Always check command exit codes
- Provide clear error messages for each failure point
- Offer alternative resolution strategies

### Context Preservation
- Save conflict analysis in temporary files
- Keep track of resolution decisions for consistency
- Document any breaking changes or compatibility concerns

### Automation Opportunities
- Use `git rerere` for repeated conflict patterns
- Leverage project-specific merge strategies
- Automate testing after each file resolution

### Communication
- Provide progress updates during long operations
- Explain resolution rationale for complex conflicts
- Suggest follow-up actions or testing recommendations

## Common Conflict Scenarios

### Package Dependency Conflicts
```bash
# package.json or pom.xml version mismatches
# Strategy: Use semantic versioning rules, prefer compatible versions
```

### Code Style Conflicts
```bash
# ESLint, formatting differences
# Strategy: Apply current repository's style guide
```

### Feature Integration Conflicts
```bash
# New features conflicting with existing code
# Strategy: Merge functionality, maintain backward compatibility
```

### Database Schema Conflicts
```bash
# JPA entity or migration conflicts
# Strategy: Preserve data integrity, use additive changes
```

## Troubleshooting

### Common Issues
1. **Permission denied**: Check git credentials and repository access
2. **Merge tool conflicts**: Use `git config merge.tool` to set preferred tool
3. **Large binary conflicts**: Consider using Git LFS or manual resolution
4. **Circular dependencies**: Review and resolve in dependency files first

### Recovery Commands
```bash
# Abort merge if needed
git merge --abort

# Reset to clean state
git reset --hard HEAD

# Clean untracked files
git clean -fd
```

This workflow ensures systematic, safe resolution of PR merge conflicts while maintaining the integrity of the crypto-web-application codebase.

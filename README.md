# Jenkins Pipeline Job Creation System

## Architecture Overview

### 1. Pipeline Script Template
**File:** `job-builder.groovy`

- **Type:** Parameterized Pipeline Script
- **Parameters:**
  - `git-path` - Git repository path
  - `pipeline-parameter-file-path` - Path to pipeline configuration file
- **Function:** Creates new jobs using Job DSL from `job-dsl.groovy`

### 2. Job DSL Script
**File:** `job-dsl.groovy`

- **Type:** Job DSL Script
- **Function:** 
  - Receives parameters from pipeline configuration
  - Creates specific job types based on configuration

### 3. Configuration Data
**File:** `pipeline-parameter.yaml`

- **Format:** YAML configuration file
- **Purpose:** Provides dynamic configuration data including:
  - Job name
  - Job DSL file path
  - OCP (OpenShift) configurations
  - Other job-specific parameters

### 4. Pipeline Execution Template
**File:** `template.groovy`

- **Function:** Defines actual execution stages and steps
- **Stages:**
  - Prepare environment
  - Build artifact
  - Build image
  - Additional execution steps

## Key Plugins Required

- **DSL Job Plugin** - For Job DSL functionality
- **Utilities Step Plugin** - For additional pipeline utilities

## Code Example

```groovy
// Read YAML File as Object
def configVal = readYaml file: "full path of the yml file"

## Reference
- https://github.com/jenkinsci/job-dsl-plugin/wiki/User-Power-Moves

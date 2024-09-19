#!/usr/bin/env node

/**
 * This script is used to reset the project to a blank state.
 * It deletes the /app directory if it exists.
 * It ensures that App.js is created in the root directory if it does not already exist.
 */

const fs = require("fs");
const path = require("path");

const root = process.cwd();
const oldDirPath = path.join(root, "app");
const appPath = path.join(root, "App.js");

// Function to handle directory operations
const resetProject = () => {
  if (fs.existsSync(oldDirPath)) {
    fs.rm(oldDirPath, { recursive: true, force: true }, (error) => {
      if (error) {
        return console.error(`Error removing directory: ${error}`);
      }
      console.log("/app directory deleted.");

      // Create App.js if it does not already exist
      if (!fs.existsSync(appPath)) {
        const appContent = `import React from 'react';
import { Text, View } from 'react-native';

export default function App() {
  return (
    <View
      style={{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Text>Edit App.js to change this screen.</Text>
    </View>
  );
}
`;

        fs.writeFile(appPath, appContent, (error) => {
          if (error) {
            return console.error(`Error creating App.js: ${error}`);
          }
          console.log("App.js created in the root directory.");
        });
      } else {
        console.log("App.js already exists in the root directory.");
      }
    });
  } else {
    console.log("/app directory does not exist.");

    // Ensure App.js is created if it does not exist
    if (!fs.existsSync(appPath)) {
      const appContent = `import React from 'react';
import { Text, View } from 'react-native';

export default function App() {
  return (
    <View
      style={{
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Text>Edit App.js to change this screen.</Text>
    </View>
  );
}
`;

      fs.writeFile(appPath, appContent, (error) => {
        if (error) {
          return console.error(`Error creating App.js: ${error}`);
        }
        console.log("App.js created in the root directory.");
      });
    }
  }
};

// Execute the function
resetProject();

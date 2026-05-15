        rules_version = '2';
        service cloud.firestore {
          match /databases/{database}/documents {
            match /{document=**} {
              allow read, write: if true; // Change this to 'if request.auth != null' for production
            }
          }
        }
        
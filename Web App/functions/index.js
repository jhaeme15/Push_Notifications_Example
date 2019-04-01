// Author: Kyle Workman, Jared Haeme
// Cloud Function Example for Firebase Notifications

let functions = require('firebase-functions');
let admin = require('firebase-admin');

admin.initializeApp();

exports.sendNotification = functions.database.ref('/{uid}/notify').onCreate((snapshot, context) => {
	
	if(snapshot.hasChildren()) {
		//get the token + message
		const title = snapshot.child('message').val();
		const token = snapshot.child('token').val();

		if (token !== null) {
			//send the message
			console.log("Construction the notification message.");
			const payload = {
				notification: {
					title: title,
					sound: 'default'
				},
			};

			console.log(payload);
	
			return admin.messaging().sendToDevice(token, payload)
						.then(function(response) {
                            console.log("Successfully sent message:", response);
                            snapshot.ref.remove();
                            return true;
				  		})
				  		.catch(function(error) {
                            console.log("Error sending message:", error);
                            snapshot.ref.remove();
				  		});
			}
	}
});
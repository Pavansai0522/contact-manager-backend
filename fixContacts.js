const mongoose = require('mongoose');
require('dotenv').config();
const Contact = require('./models/Contact');

mongoose.connect(process.env.MONGO_URI)
  .then(async () => {
    const result = await Contact.updateMany(
      {
        $or: [
          { contactStatus: { $exists: false } },
          { contactStatus: '' }
        ]
      },
      { $set: { contactStatus: 'New Lead' } }
    );

    console.log(`✅ Updated ${result.modifiedCount} contacts`);
    mongoose.disconnect();
  })
  .catch(err => {
    console.error('❌ Connection error:', err);
  });

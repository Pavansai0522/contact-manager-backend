const mongoose = require('mongoose');

const ContactSchema = new mongoose.Schema({
  firstName: String,
  lastName: String,
  email: { type: String, required: true },
  emailStatus: { type: String, enum: ['Subscribed', 'Unsubscribed', 'Not Specified'], default: 'Subscribed' },
  list: String,
  phone: String,
  contactStatus: String,
  tags: [String],
});

module.exports = mongoose.model('Contact', ContactSchema);

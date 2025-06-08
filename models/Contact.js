const mongoose = require('mongoose');

const ContactSchema = new mongoose.Schema({
  firstName: { type: String, required: true },
  lastName:  { type: String, required: true },
  email:     { type: String, required: true },
  emailStatus: {
    type: String,
    enum: ['Subscribed', 'Unsubscribed', 'Not Specified'],
    default: 'Subscribed'
  },
  list: String,
  phone: String,
  contactStatus: {
    type: String,
    enum: ['New Lead', 'Engaged Lead', 'Stale Lead', 'New Sale', 'Active', 'Inactive'],
    default: 'New Lead'
  }
}, { timestamps: true });

module.exports = mongoose.model('Contact', ContactSchema);

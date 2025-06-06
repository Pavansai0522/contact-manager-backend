// server.js
const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Contact = require('./models/Contact');
require('dotenv').config();

// Debug line to ensure MONGO_URI is loaded
console.log("MONGO_URI:", process.env.MONGO_URI);

const app = express();
app.use(cors());
app.use(express.json());

// Fix: Ensure URI is passed correctly
mongoose.connect(process.env.MONGO_URI)
  .then(() => console.log('✅ Connected to MongoDB'))
  .catch((err) => console.error('❌ MongoDB connection error:', err));


app.get('/', (req, res) => {
  res.send('✅ Contact Manager API is running');
});

app.post('/contacts', async (req, res) => {
  try {
    const contact = new Contact(req.body);
    await contact.save();
    res.status(201).send(contact);
  } catch (err) {
    res.status(400).send(err);
  }
});

app.get('/contacts', async (req, res) => {
  const { emailStatus, contactStatus, page = 1, tags, search } = req.query;
  const filter = {};
  if (emailStatus) filter.emailStatus = emailStatus;
  if (contactStatus) filter.contactStatus = contactStatus;
  if (search) {
    const regex = new RegExp(search, 'i');
    filter.$or = [
      { firstName: regex },
      { lastName: regex },
      { email: regex }
    ];
  }
  if (tags) {
    const tagList = tags.split(',').map(t => t.trim());
    filter.tags = { $in: tagList };
  }
  const limit = 15;
  const skip = (page - 1) * limit;
  const total = await Contact.countDocuments(filter);
  const contacts = await Contact.find(filter).skip(skip).limit(limit);
  res.send({ contacts, totalPages: Math.ceil(total / limit) });
});

app.put('/contacts/:id', async (req, res) => {
  try {
    const contact = await Contact.findByIdAndUpdate(req.params.id, req.body, { new: true });
    res.send(contact);
  } catch (err) {
    res.status(400).send(err);
  }
});

app.delete('/contacts/:id', async (req, res) => {
  try {
    await Contact.findByIdAndDelete(req.params.id);
    res.send({ message: 'Contact deleted' });
  } catch (err) {
    res.status(400).send(err);
  }
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`🚀 Server running on port ${PORT}`));
// Export the app for testing
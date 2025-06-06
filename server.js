const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Contact = require('./models/Contact');
require('dotenv').config();
// server.js
const app = express();
app.use(cors());
app.use(express.json());

mongoose.connect(process.env.MONGO_URI, {
  useNewUrlParser: true,
  useUnifiedTopology: true
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
  const { emailStatus, contactStatus, page = 1 } = req.query;
  const filter = {};
  if (emailStatus) filter.emailStatus = emailStatus;
  if (contactStatus) filter.contactStatus = contactStatus;
  if (req.query.search) {
    const regex = new RegExp(req.query.search, 'i');
    filter.$or = [
      { firstName: regex },
      { lastName: regex },
      { email: regex }
    ];
  }

  const limit = 5;
  const skip = (page - 1) * limit;
  const total = await Contact.countDocuments(filter);
  const contacts = await Contact.find(filter).skip(skip).limit(limit);
  if (req.query.tags) {
    const tagList = req.query.tags.split(',').map(t => t.trim());
    filter.tags = { $in: tagList };
  }
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

app.listen(5000, () => console.log('Server running on port 5000'));

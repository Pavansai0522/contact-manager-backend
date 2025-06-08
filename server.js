const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
const Contact = require('./models/Contact');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

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
  const { emailStatus, contactStatus, page = 1, search } = req.query;
  const filter = {};
  if (emailStatus) filter.emailStatus = emailStatus;
  if (contactStatus) filter.contactStatus = contactStatus;
  if (search) {
    const regex = new RegExp(search, 'i');
    filter.$or = [{ firstName: regex }, { lastName: regex }, { email: regex }];
  }

  const limit = parseInt(req.query.limit) || 15;
  console.log('🔍 Requested limit:', req.query.limit, '→ Parsed limit:', limit);

  const skip = (page - 1) * limit;
  console.log('📄 Page:', page, 'Skip:', skip, 'Limit:', limit);
  const total = await Contact.countDocuments(filter);
  const contacts = await Contact.find(filter).skip(skip).limit(limit);
  res.send({ contacts, totalPages: Math.ceil(total / limit),totalContacts: total });
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

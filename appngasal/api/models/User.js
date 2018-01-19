/**
 * User.js
 *
 * @description :: TODO: You might write a short summary of how this model works and what it represents here.
 * @docs        :: http://sailsjs.org/documentation/concepts/models-and-orm/models
 */
var bcrypt = require('bcrypt');
module.exports = {
  
  attributes: {
    email:{
      type:'string',
      email: true,
      required:true,
      unique:true
    },
    password:{
      type:'string',
      required:true
    },
    name:{
      type:'string',
      required:true
    },
    confirmed:{
      type:'string'
    },
    balance:{
      type:'integer'
    },
    birth_date:{
      type:'string'

    },
    photo:{
      type:'string'

    },
    phone_number:{
      type:'string'

    },
    sukses:{
      type:'integer'
    },
    batal:{
      type:'integer'
    },
    toJSON: function(){
      var obj = this.toObject();
      delete obj.password;
      delete obj.csrf;
      return obj;

    
    },
    
    
    
  },
   beforeCreate: function(user, cb) {
      bcrypt.genSalt(10, function(err, salt) {
          bcrypt.hash(user.password, salt, function(err, hash) {
            if(err) {
                console.log(err);
                cb(err);
            } else {
                user.password = hash;
                console.log(hash);
                cb(null, user);
            }
          });
      });
  },
  comparePassword : function (password, user, cb) {
    bcrypt.compare(password, user.password, function (err, match) {

      if(err) cb(err);
      if(match) {
        cb(null, true);
      } else {
        cb(err);
      }
    });
  },
  connection:'appngasaldb'
};

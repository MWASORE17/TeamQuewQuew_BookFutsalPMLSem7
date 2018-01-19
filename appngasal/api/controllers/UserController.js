/**
 * UserController
 *
 * @description :: Server-side logic for managing users
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
    
	create: function(req, res, next){
    var email = req.param('email')
       User.create(req.body).exec(function (err, user) {
        if (err) {
          return res.json(err.status, {err: err});
        }
        // If user created successfuly we return user and token as response
        if (user) {
          // NOTE: payload is { id: user.id}
          res.send('true');
        }
      });
        
    },
    upload: function(req, res) {
        req.file('file') // this is the name of the file in your multipart form
        .upload({ dirname: '../../assets/images/us' }, function(err, uploads) {
          // try to always handle errors
          if (err) { return res.serverError(err) }
          // uploads is an array of files uploaded 
          // so remember to expect an array object
          if (uploads.length === 0) { return res.badRequest('No file was uploaded') }
          // if file was uploaded create a new registry
          // at this point the file is phisicaly available in the hard drive
          var id =User.id;
          var photo = User.photo;
          var fd = uploads[0].fd;
          var nameImage = fd.substring(78)
          
          User.update({id:req.param('id')}
                    ,
                    {photo: "http://10.0.2.2:1337/images/us/"+nameImage
                  }).exec(function(err, file) {
                    if (err) { return res.serverError(err) }
                    // if it was successful return the registry in the response
                    return res.json(file)
        })
        })
        
    },
    edit: function(req, res, next){
        var email = req.param('email')
        User.findOne({email:email}).exec(function(err,user){
            if (err) {
                return res.serverError(err);
              }
              if (!user) {
                return res.notFound('Could not find email, sorry.');
              }
            
              //sails.log('Found "%s"', finn.fullName);
              return res.json(user);
            });
            
        },
        // User.findOne(req.param('email'), function foundUsers(err,user){
        //     if(err) return next(err);
        //     if(!user) return res.send(404,'User doesn\'t exist.');
        //      res.view({
        //         user: user
        //     });
        // });
    
    
    update: function(req, res, next){
        User.update(req.param('id'),req.params.all(), function userUpdated(err,user){
            if(err){
                return res.redirect('/user/' + req.param('id'));
            }
			if(user)
				res.json(user);
        });
    },
    delete: function(req, res, next){
        var id = req.param('id');
        User.findOne(id, function foundUsers(err,user){
            if(err) return next(err);

            if(!user) return res.send(404,'User doesn\'t exist.');

            User.destroy(req.param('id'), function userDestroyed(err){
                if(err) return next(err);
            });
            res.send(200,'user id '+ id + ' has been deleted')
            //res.json(202,user);
        });
    },
    login : function(req,res){
        
    }
       
    
      
};


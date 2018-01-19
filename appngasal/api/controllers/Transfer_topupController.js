/**
 * Transfer_topupController
 *
 * @description :: Server-side logic for managing transfer_topups
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
    ganti: function(req, res, next){
        var total = req.param('total_transfer');
        User.findOne(req.param('user_id'), function foundUser(err,user){
            if(err) return next(err);
            if(!user) return next('User doesn\'t exist.');

            console.log(user.balance);
            console.log(total);
            
            var blance = parseInt(user.balance);
            blance = blance+parseInt(total);
            console.log(blance)
            User.update({id:req.param('user_id')}
            ,
            {
                balance:parseInt(blance)

          }).exec(function(err, user) {
            if (err) { return res.serverError(err) }
            // if it was successful return the registry in the response
            console.log(user)
})
    });
        Transfer_topup.update(req.param('id'),req.params.all(), function transferTopupUpdated(err,transfer_topup){
            if(err){
                return res.redirect('/transfer_topup/' + req.param('id'));
            }
            res.redirect('admin/topup/')
        });
    },
    findall: function (req, res) {
        console.log("Inside findall..............");

        return Transfer_topup.find().then(function (transfer_topup) {
            console.log("transfer_topupService.findAll -- transfer_topup = " + transfer_topup);
            return res.view("admin/topup/list", {
                status: 'OK',
                title: 'List of Topup',
                transfer_topup: transfer_topup
            });
        }).catch(function (err) {
            console.error("Error on ContactService.findAll");
            console.error(err);
            return res.view('500', {message: "Sorry, an error occurd - " + err});
        });
    },
	create: function(req, res, next){
        Transfer_topup.create(req.params.all(),function transferTopupCreated(err, transferTopup){
            if(err){
                console.log(err)
            
        }
        res.json(201,transferTopup);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        
        var user_id = req.param('user_id')
        Transfer_topup.find({user_id:user_id}).exec(function(err,transferTopup){
            if (err) {
                return res.serverError(err);
              }
              if (!transferTopup) {
                return res.notFound('Could not find user_id, sorry.');
              }
            
              //sails.log('Found "%s"', finn.fullName);
              return res.json(transferTopup);
            });
    },

    update: function(req, res, next){
        Transfer_topup.update(req.param('id'),req.params.all(), function transferTopupUpdated(err,transferTopup){
            if(err){
                return res.redirect('/transfer_topup/' + req.param('id'));
            }
            res.json(201,transferTopup);
        });
    },
    delete: function(req, res, next){
        Transfer_topup.findOne(req.param('id'), function foundtransferTopup(err,transferTopup){
            if(err) return next(err);

            if(!transferTopup) return next('transferTopup doesn\'t exist.');

            Transfer_topup.destroy(req.param('id'), function transferTopupDestroyed(err){
                if(err) return next(err);
            });
            res.redirect('admin/topup/');
        });
    }
    
};


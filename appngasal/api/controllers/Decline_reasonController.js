/**
 * Decline_reasonController
 *
 * @description :: Server-side logic for managing decline_reasons
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Decline_reason.create(req.params.all(),function declinereasonCreated(err, declinereason){
            if(err){
                console.log(err)
            
        }
        res.json(201,declinereason);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        Decline_reason.findOne(req.param('id'), function founddeclinereason(err,declinereason){
            if(err) return next(err);
            if(!declinereason) return next('declinereason doesn\'t exist.');
            // res.view({
            //     user: user
            // });
        });
    },

    update: function(req, res, next){
        Decline_reason.update(req.param('id'),req.params.all(), function declinereasonUpdated(err){
            if(err){
                return res.redirect('/decline_reason/' + req.param('id'));
            }
            res.json(201,declinereason);
        });
    },
    delete: function(req, res, next){
        Decline_reason.findOne(req.param('id'), function founddeclinereason(err,declinereason){
            if(err) return next(err);

            if(!declinereason) return next('declinereason doesn\'t exist.');

            Decline_reason.destroy(req.param('id'), function declinereasonDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,declinereason);
        });
    }
};


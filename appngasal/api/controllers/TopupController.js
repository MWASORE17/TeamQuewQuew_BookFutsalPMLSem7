/**
 * TopupController
 *
 * @description :: Server-side logic for managing topups
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Topup.create(req.params.all(),function topupCreated(err, topup){
            if(err){
                console.log(err)
            
        }
        res.json(201,topup);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        Topup.findOne(req.param('id'), function foundTopup(err,topup){
            if(err) return next(err);
            if(!topup) return next('Topup doesn\'t exist.');
            // res.view({
            //     user: user
            // });
        });
    },

    update: function(req, res, next){
        Topup.update(req.param('id'),req.params.all(), function topupUpdated(err){
            if(err){
                return res.redirect('/topup/' + req.param('id'));
            }
            res.json(201,topup);
        });
    },
    delete: function(req, res, next){
        Topup.findOne(req.param('id'), function foundTopup(err,topup){
            if(err) return next(err);

            if(!topup) return next('Topup doesn\'t exist.');

            Topup.destroy(req.param('id'), function topupDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,topup);
        });
    }
};


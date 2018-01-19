/**
 * Expired_informationController
 *
 * @description :: Server-side logic for managing expired_informations
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Expired_information.create(req.params.all(),function expiredinformationCreated(err, expiredinformation){
            if(err){
                console.log(err)
            
        }
        res.json(201,expiredinformation);
        //res.send('oke')
        
        });
    },
    edit: function(req, res, next){
        Expired_information.findOne(req.param('id'), function foundexpiredinformation(err,expiredinformation){
            if(err) return next(err);
            if(!expiredinformation) return next('expiredinformation doesn\'t exist.');
            // res.view({
            //     user: user
            // });
        });
    },

    update: function(req, res, next){
        Expired_information.update(req.param('id'),req.params.all(), function expiredinformationUpdated(err){
            if(err){
                return res.redirect('/Expired_information/' + req.param('id'));
            }
            res.json(201,expiredinformation);
        });
    },
    delete: function(req, res, next){
        Expired_information.findOne(req.param('id'), function foundexpiredinformation(err,expiredinformation){
            if(err) return next(err);

            if(!expiredinformation) return next('expiredinformation doesn\'t exist.');

            Expired_information.destroy(req.param('id'), function expiredinformationDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,expiredinformation);
        });
    }
};


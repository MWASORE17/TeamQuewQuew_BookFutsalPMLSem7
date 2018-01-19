/**
 * AdminController
 *
 * @description :: Server-side logic for managing admins
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
	create: function(req, res, next){
        Admin.create(req.params.all(),function adminCreated(err, admin){
            if(err){
                console.log(err)
            
        }
        res.json(201,admin);
        
        });
    },
    edit: function(req, res, next){
        Admin.findOne(req.param('id'), function foundAdmin(err,admin){
            if(err) return next(err);
            if(!admin) return next('Admin doesn\'t exist.');
            // res.view({
            //     booking: booking
            // });
        });
    },

    update: function(req, res, next){
        Admin.update(req.param('id'),req.params.all(), function adminUpdated(err){
            if(err){
                return res.redirect('/admin/' + req.param('id'));
            }
            res.json(201,admin);
        });
    },
    delete: function(req, res, next){
        Admin.findOne(req.param('id'), function foundAdmin(err,admin){
            if(err) return next(err);

            if(!admin) return next('Admin doesn\'t exist.');

            Admin.destroy(req.param('id'), function adminDestroyed(err){
                if(err) return next(err);
            });
            res.json(202,admin);
        });
    }
};


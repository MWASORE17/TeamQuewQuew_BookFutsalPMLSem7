/**
 * Futsal_fieldController
 *
 * @description :: Server-side logic for managing futsal_fields
 * @help        :: See http://sailsjs.org/#!/documentation/concepts/Controllers
 */

module.exports = {
    add : function (req, res) {
        console.log("Inside new..............");
        return res.view("admin/lapangan/add", {
            status: 'OK',
            title: 'Add a new Lapangan'
        });
    },
    findall: function (req, res) {
        console.log("Inside findall..............");

        return Futsal_field.find().then(function (futsal_field) {
            console.log("Futsal_fieldService.findAll -- futsal_field = " + futsal_field);
            return res.view("admin/lapangan/list", {
                status: 'OK',
                title: 'List of Lapangan',
                futsal_field: futsal_field
            });
        }).catch(function (err) {
            console.error("Error on ContactService.findAll");
            console.error(err);
            return res.view('500', {message: "Sorry, an error occurd - " + err});
        });
    },
	tambah: function(req, res, next){
        req.file('photo_url') // this is the name of the file in your multipart form
        .upload({ dirname: '../../assets/images/lapangan' }, function(err, uploads) {
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
          var nameImage = fd.substring(84)

          Futsal_field.create({
            futsal_name: req.param('futsal_name'),
            districts: req.param('districts'),
            address: req.param('address'),
            type_field : req.param('type_field'),
            price : req.param('price'),
            description : req.param('description'),
            photo_url : "http://10.0.2.2:1337/images/lapangan/"+nameImage,
          }).exec(function(err, futsalField) {
            if (err) { return res.serverError(err) }
            // if it was successful return the registry in the response
            return res.redirect("admin/lapangan/")
          })  
        })
    },
    ganti: function(req, res, next){
        req.file('photo_url') // this is the name of the file in your multipart form
        .upload({ dirname: '../../assets/images/lapangan' }, function(err, uploads) {
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
          var nameImage = fd.substring(84)

          Futsal_field.update({id:req.param('id')},
          {
            futsal_name: req.param('futsal_name'),
            districts: req.param('districts'),
            address: req.param('address'),
            type_field : req.param('type_field'),
            price : req.param('price'),
            description : req.param('description'),
            photo_url : "http://10.0.2.2:1337/images/lapangan/"+nameImage,
          }).exec(function(err, futsalField) {
            if (err) { return res.serverError(err) }
            // if it was successful return the registry in the response
            return res.redirect("admin/lapangan/")
          })  
        })
    },
    ubah: function(req, res, next){
        Futsal_field.findOne(req.param('id'), function foundFutsalField(err,futsal_field){
            if(err) return next(err);
            if(!futsal_field) return next('Futsal Field doesn\'t exist.');
            res.view("admin/lapangan/edit/", {
                status: 'OK',
                title: 'Edit Lapangan',
                futsal_field: futsal_field
            });
        });
    },

    update: function(req, res, next){
        Futsal_field.update(req.param('id'),req.params.all(), function futsalFieldUpdated(err){
            if(err){
                return res.redirect('/Futsal_field/' + req.param('id'));
            }
            res.json(201,futsalField);
        });
    },
    delete: function(req, res, next){
        Futsal_field.findOne(req.param('id'), function foundFutsalField(err,futsalField){
            if(err) return next(err);

            if(!futsalField) return next('Futsal field doesn\'t exist.');

            Futsal_field.destroy(req.param('id'), function futsalFieldDestroyed(err){
                if(err) return next(err);
            });
            return res.redirect("admin/lapangan/")
            //res.json(202,futsalField);
        });
    }
};


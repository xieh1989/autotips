$(document).ready(function() {
  $('.example-films .typeahead').typeahead([{
      name: 'best-picture-winners',
      remote: '../data/films/queries/QUERY.json',
      template: '<p><strong>{{value}}</strong> {{year}}</p>',
      engine: Hogan
    }
  ]);
});
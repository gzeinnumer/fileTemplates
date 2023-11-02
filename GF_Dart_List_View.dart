// ignore_for_file: must_be_immutable, constant_identifier_names, prefer_interpolation_to_compose_strings

import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class ${ListForm}View extends StatelessWidget {
  static const String TAG = "${ListForm}View";
  final String arguments;
  List<${ListForm}Model> data = [];

  ${ListForm}View({
    Key? key,
    required this.arguments,
  }) : super(key: key);

  final _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("${ListForm}"),
      ),
      body: _multiRepositoryProvider(context),
    );
  }

  MultiRepositoryProvider _multiRepositoryProvider(BuildContext context) {
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider(create: (context) => ${ListForm}Repo()),
      ],
      child: _multiBlocProvider(context),
    );
  }

  MultiBlocProvider _multiBlocProvider(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider<${ListForm}Bloc>(
          create: (context) => ${ListForm}Bloc(
            repo: context.read<${ListForm}Repo>(),
            arguments: arguments,
          )..add(${ListForm}EventInit()),
        )
      ],
      child: _blocListener(context),
    );
  }

  Widget _blocListener(BuildContext context) {
    return BlocListener<${ListForm}Bloc, ${ListForm}State>(
      listener: (context, state) {
        final status = state.status;
        if (status is ${ListForm}StatusInitDone) {
          final c = state.status as ${ListForm}StatusInitDone;
          data = state.data;
          FocusScope.of(context).unfocus();
        } else if (status is ${ListForm}StatusOnInput) {
          FocusScope.of(context).unfocus();
        } else if (status is ${ListForm}StatusInfo) {
          final c = state.status as ${ListForm}StatusInfo;
          String title = c.title.toString();
          String msg = c.msg.toString();
          int type = c.type!;

          ScaffoldMessenger.of(context).showSnackBar(SnackBar(
            content: Row(
              children: [
                Text("Title: $title"),
                Text("Message: $msg"),
                Text("Type: $type"),
              ],
            ),
          ));
        } else if (status is ${ListForm}StatusFillData) {
          var forWhat = status.forWhat;
          var data = status.data;
          for (int i = 0; i < forWhat.length; i++) {
            if (forWhat[i] == '_form1Controller') {}
          }
        }
      },
      child: _blocBuilder(context),
    );
  }

  Widget _blocBuilder(BuildContext context) {
    return BlocBuilder<${ListForm}Bloc, ${ListForm}State>(builder: (context, state) {
      if (state.status is ${ListForm}StatusLoading) {
        return const Center(
          child: CircularProgressIndicator(),
        );
      }
      return _body(context, state);
    });
  }

  Widget _body(BuildContext context, ${ListForm}State state) {
    return Form(
      key: _formKey,
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            listContent(state),
          ],
        ),
      ),
    );
  }

  Expanded listContent(${ListForm}State state) {
    return Expanded(
      child: ListView.builder(
        itemCount: state.data.length, // Specify the number of items in your list
        itemBuilder: (BuildContext context, int index) {
          // Define the widget for each item at the given index
          return Card(
            elevation: 4,
            margin: EdgeInsets.all(8),
            child: Column(
              children: [
                Text('Subtitle for Item $index'),
              ],
            ),
          );
        },
      ),
    );
  }
}

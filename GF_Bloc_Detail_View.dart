import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class ${DetailForm}View extends StatelessWidget {
  static const String TAG = "${DetailForm}View";
  final ${DetailForm}Model arguments;

  ${DetailForm}View({
    Key? key,
    required this.arguments,
  }) : super(key: key);
  final _formKey = GlobalKey<FormState>();

  final _form1Controller = TextEditingController();
  final _form2Controller = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Detail Form"),
      ),
      body: _multiRepositoryProvider(context),
    );
  }

  MultiRepositoryProvider _multiRepositoryProvider(BuildContext context) {
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider(create: (context) => ${DetailForm}Repo()),
      ],
      child: _multiBlocProvider(context),
    );
  }

  MultiBlocProvider _multiBlocProvider(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider<${DetailForm}Bloc>(
          create: (context) => ${DetailForm}Bloc(
            repo: context.read<${DetailForm}Repo>(),
            arguments: arguments,
          )..add(${DetailForm}EventInit()),
        )
      ],
      child: _blocListener(context),
    );
  }

  Widget _blocListener(BuildContext context) {
    return BlocListener<${DetailForm}Bloc, ${DetailForm}State>(
      listener: (context, state) {
        final status = state.status;
        if (status is ${DetailForm}StatusInitDone) {
          final c = state.status as ${DetailForm}StatusInitDone;
          _form1Controller.text = state.data!.form1.toString();
          _form2Controller.text = state.data!.form2.toString();
          FocusScope.of(context).unfocus();
        } else if (status is ${DetailForm}StatusOnInput) {
          FocusScope.of(context).unfocus();
        } else if (status is ${DetailForm}StatusInfo) {
          final c = state.status as ${DetailForm}StatusInfo;
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
        } else if (status is ${DetailForm}StatusFillData) {
          var forWhat = status.forWhat;
          var data = status.data;
          for (int i = 0; i < forWhat.length; i++) {
            if (forWhat[i] == '_form2Controller') {
              _form2Controller.text = data[i];
            }
          }
        }
      },
      child: _blocBuilder(context),
    );
  }

  Widget _blocBuilder(BuildContext context) {
    return BlocBuilder<${DetailForm}Bloc, ${DetailForm}State>(builder: (context, state) {
      if (state.status is ${DetailForm}StatusLoading) {
        return const Center(
          child: CircularProgressIndicator(),
        );
      }
      return _body(context, state);
    });
  }

  Widget _body(BuildContext context, ${DetailForm}State state) {
    return Form(
      key: _formKey,
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            _form1(context, state),
            _form2(context, state),
          ],
        ),
      ),
    );
  }

  Padding _form1(BuildContext context, ${DetailForm}State state) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: TextFormField(
        readOnly: true,
        controller: _form1Controller,
        validator: (value) => value.toString().isNotEmpty ? null : "Required",
        onChanged: (value) => context.read<${DetailForm}Bloc>().add(${DetailForm}EventInputForm1(value)),
        decoration: const InputDecoration(
          labelText: 'Form 1',
          border: OutlineInputBorder(),
        ),
      ),
    );
  }

  Widget _form2(BuildContext context, ${DetailForm}State state) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: TextFormField(
        readOnly: true,
        controller: _form2Controller,
        validator: (value) => value.toString().isNotEmpty ? null : "Required",
        onChanged: (value) => context.read<${DetailForm}Bloc>().add(${DetailForm}EventSelectForm2(value)),
        decoration: const InputDecoration(
          labelText: 'Form 2',
          border: OutlineInputBorder(),
        ),
      ),
    );
  }
}

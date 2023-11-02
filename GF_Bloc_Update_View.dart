import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class ${UpdateForm}View extends StatelessWidget {
  static const String TAG = "${UpdateForm}View";
  final ${UpdateForm}Model arguments;

  ${UpdateForm}View({
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
        title: const Text("Update Form"),
      ),
      body: _multiRepositoryProvider(context),
    );
  }

  MultiRepositoryProvider _multiRepositoryProvider(BuildContext context) {
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider(create: (context) => ${UpdateForm}Repo()),
      ],
      child: _multiBlocProvider(context),
    );
  }

  MultiBlocProvider _multiBlocProvider(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider<${UpdateForm}Bloc>(
          create: (context) => ${UpdateForm}Bloc(
            repo: context.read<${UpdateForm}Repo>(),
            arguments: arguments,
          )..add(${UpdateForm}EventInit()),
        )
      ],
      child: _blocListener(context),
    );
  }

  Widget _blocListener(BuildContext context) {
    return BlocListener<${UpdateForm}Bloc, ${UpdateForm}State>(
      listener: (context, state) {
        final status = state.status;
        if (status is ${UpdateForm}StatusInitDone) {
          final c = state.status as ${UpdateForm}StatusInitDone;
          _form1Controller.text = state.data!.form1.toString();
          _form2Controller.text = state.data!.form2.toString();
          FocusScope.of(context).unfocus();
        } else if (status is ${UpdateForm}StatusOnInput) {
          FocusScope.of(context).unfocus();
        } else if (status is ${UpdateForm}StatusInfo) {
          final c = state.status as ${UpdateForm}StatusInfo;
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
        } else if (status is ${UpdateForm}StatusFillData) {
          var forWhat = status.forWhat;
          var data = status.data;
          for (int i = 0; i < forWhat.length; i++) {
            if (forWhat[i] == '_form1Controller') {
              _form1Controller.text = data[i];
            }
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
    return BlocBuilder<${UpdateForm}Bloc, ${UpdateForm}State>(builder: (context, state) {
      if (state.status is ${UpdateForm}StatusLoading) {
        return const Center(
          child: CircularProgressIndicator(),
        );
      }
      return _body(context, state);
    });
  }

  Widget _body(BuildContext context, ${UpdateForm}State state) {
    return Form(
      key: _formKey,
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            _form1(context, state),
            _form2(context, state),
            _submit(context, state),
            _reset(context),
          ],
        ),
      ),
    );
  }

  Padding _form1(BuildContext context, ${UpdateForm}State state) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: TextFormField(
        controller: _form1Controller,
        validator: (value) => value.toString().isNotEmpty ? null : "Required",
        onChanged: (value) => context.read<${UpdateForm}Bloc>().add(${UpdateForm}EventInputForm1(value)),
        decoration: const InputDecoration(
          labelText: 'Form 1',
          border: OutlineInputBorder(),
        ),
      ),
    );
  }

  Widget _form2(BuildContext context, ${UpdateForm}State state) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: Row(
        children: [
          Expanded(
            child: TextFormField(
              readOnly: true,
              controller: _form2Controller,
              validator: (value) => value.toString().isNotEmpty ? null : "Required",
              onChanged: (value) => context.read<${UpdateForm}Bloc>().add(${UpdateForm}EventSelectForm2(value)),
              decoration: const InputDecoration(
                labelText: 'Form 2',
                border: OutlineInputBorder(),
              ),
            ),
          ),
          const SizedBox(width: 16),
          ElevatedButton(
            onPressed: () {
              _form2Controller.text = "Fill Form 2";
            },
            child: const Text("Fill Form 2"),
          ),
        ],
      ),
    );
  }

  ElevatedButton _submit(BuildContext context, ${UpdateForm}State state) {
    return ElevatedButton(
      onPressed: () {
        FocusScope.of(context).unfocus();
        if (_formKey.currentState!.validate()) {
          context.read<${UpdateForm}Bloc>().add(${UpdateForm}EventSubmit());
        } else {
          ScaffoldMessenger.of(context).showSnackBar(const SnackBar(
            content: Text("Form Required"),
          ));
        }
      },
      child: const Text("Submit"),
    );
  }

  ElevatedButton _reset(BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        _form1Controller.text = "";
        _form2Controller.text = "";
        _formKey.currentState?.reset();
        context.read<${UpdateForm}Bloc>().add(${UpdateForm}EventReset());
        FocusScope.of(context).unfocus();
      },
      child: const Text("Reset"),
    );
  }
}

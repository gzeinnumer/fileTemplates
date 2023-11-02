import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class ${CreateForm}View extends StatelessWidget {
  static const String TAG = "${CreateForm}View";
  final String arguments;

  ${CreateForm}View({
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
        title: const Text("Create Form"),
      ),
      body: _multiRepositoryProvider(context),
    );
  }

  MultiRepositoryProvider _multiRepositoryProvider(BuildContext context) {
    return MultiRepositoryProvider(
      providers: [
        RepositoryProvider(create: (context) => ${CreateForm}Repo()),
      ],
      child: _multiBlocProvider(context),
    );
  }

  MultiBlocProvider _multiBlocProvider(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider<${CreateForm}Bloc>(
          create: (context) => ${CreateForm}Bloc(
            repo: context.read<${CreateForm}Repo>(),
            arguments: arguments,
          )..add(${CreateForm}EventInit()),
        )
      ],
      child: _blocListener(context),
    );
  }

  Widget _blocListener(BuildContext context) {
    return BlocListener<${CreateForm}Bloc, ${CreateForm}State>(
      listener: (context, state) {
        final status = state.status;
        if (status is ${CreateForm}StatusInitDone) {
          final c = state.status as ${CreateForm}StatusInitDone;
          FocusScope.of(context).unfocus();
        } else if (status is ${CreateForm}StatusOnInput) {
          FocusScope.of(context).unfocus();
        } else if (status is ${CreateForm}StatusInfo) {
          final c = state.status as ${CreateForm}StatusInfo;
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
        } else if (status is ${CreateForm}StatusFillData) {
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
    return BlocBuilder<${CreateForm}Bloc, ${CreateForm}State>(builder: (context, state) {
      if (state.status is ${CreateForm}StatusLoading) {
        return const Center(
          child: CircularProgressIndicator(),
        );
      }
      return _body(context, state);
    });
  }

  Widget _body(BuildContext context, ${CreateForm}State state) {
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

  Padding _form1(BuildContext context, ${CreateForm}State state) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: TextFormField(
        controller: _form1Controller,
        validator: (value) => value.toString().isNotEmpty ? null : "Required",
        onChanged: (value) => context.read<${CreateForm}Bloc>().add(${CreateForm}EventInputForm1(value)),
        decoration: const InputDecoration(
          labelText: 'Form 1',
          border: OutlineInputBorder(),
        ),
      ),
    );
  }

  Widget _form2(BuildContext context, ${CreateForm}State state) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 16),
      child: Row(
        children: [
          Expanded(
            child: TextFormField(
              readOnly: true,
              controller: _form2Controller,
              validator: (value) => value.toString().isNotEmpty ? null : "Required",
              onChanged: (value) => context.read<${CreateForm}Bloc>().add(${CreateForm}EventSelectForm2(value)),
              decoration: const InputDecoration(
                labelText: 'Form 2',
                border: OutlineInputBorder(),
              ),
            ),
          ),
          SizedBox(
            width: 16,
          ),
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

  ElevatedButton _submit(BuildContext context, ${CreateForm}State state) {
    return ElevatedButton(
      onPressed: () {
        FocusScope.of(context).unfocus();
        if (_formKey.currentState!.validate()) {
          context.read<${CreateForm}Bloc>().add(${CreateForm}EventSubmit());
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
        // _form1Controller.text = "";
        // _form2Controller.text = "";
        _formKey.currentState?.reset();
        context.read<${CreateForm}Bloc>().add(${CreateForm}EventReset());
        FocusScope.of(context).unfocus();
      },
      child: const Text("Reset"),
    );
  }
}

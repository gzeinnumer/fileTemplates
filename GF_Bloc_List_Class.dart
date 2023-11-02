// ignore_for_file: must_be_immutable, constant_identifier_names, prefer_interpolation_to_compose_strings

import 'package:flutter_bloc/flutter_bloc.dart';

abstract class ${ListForm}Event {}

//EVENT-------------------------------------------------------------------------
class ${ListForm}EventInit extends ${ListForm}Event {
  ${ListForm}EventInit();
}

class ${ListForm}EventInputForm1 extends ${ListForm}Event {
  final String value;

  ${ListForm}EventInputForm1(this.value);
}

class ${ListForm}EventSelectForm2 extends ${ListForm}Event {
  final String value;

  ${ListForm}EventSelectForm2(this.value);
}

class ${ListForm}EventReset extends ${ListForm}Event {}

class ${ListForm}EventSubmit extends ${ListForm}Event {}

//STATUS------------------------------------------------------------------------
abstract class ${ListForm}Status {
  const ${ListForm}Status();
}

class ${ListForm}StatusInit extends ${ListForm}Status {
  const ${ListForm}StatusInit();
}

class ${ListForm}StatusInitDone extends ${ListForm}Status {
  const ${ListForm}StatusInitDone();
}

class ${ListForm}StatusFillData extends ${ListForm}Status {
  final List<String> forWhat;
  final List<dynamic> data;

  const ${ListForm}StatusFillData({
    this.forWhat = const [],
    this.data = const [],
  });
}

class ${ListForm}StatusOnInput extends ${ListForm}Status {
  const ${ListForm}StatusOnInput();
}

class ${ListForm}StatusLoading extends ${ListForm}Status {
  const ${ListForm}StatusLoading();
}

class ${ListForm}StatusInfo extends ${ListForm}Status {
  final String? _title;
  final String? _msg;
  final int? _type;

  //type 1 = success->back
  //type 2 = info->stay
  //type 3 = info->back
  //type 4 = confirm
  //type 5 = snackbar
  //type 6 = action

  String? get title => _title;

  String? get msg => _msg;

  int? get type => _type;

  ${ListForm}StatusInfo(this._title, this._msg, this._type);
}

//STATE--------------------------------------------------------------------------
class ${ListForm}State {
  final String edForm1;

  final String edForm2;

  List<${ListForm}Model> data;
  final ${ListForm}Status status;

  ${ListForm}State({
    this.edForm1 = "",
    this.edForm2 = "",
    this.data = const [],
    this.status = const ${ListForm}StatusInit(),
  });

  ${ListForm}State copyWith({
    String? edForm1,
    String? edForm2,
    List<${ListForm}Model>? data,
    ${ListForm}Status? status,
  }) {
    return ${ListForm}State(
      edForm1: edForm1 ?? this.edForm1,
      edForm2: edForm2 ?? this.edForm2,
      data: data ?? this.data,
      status: status ?? this.status,
    );
  }
}

//BLOC--------------------------------------------------------------------------
class ${ListForm}Bloc extends Bloc<${ListForm}Event, ${ListForm}State> {
  final ${ListForm}Repo repo;
  final String arguments;

  ${ListForm}Bloc({
    required this.repo,
    required this.arguments,
  }) : super(${ListForm}State());

  @override
  Stream<${ListForm}State> mapEventToState(${ListForm}Event event) async* {
    if (event is ${ListForm}EventInit) {
      yield state.copyWith(status: const ${ListForm}StatusLoading());
      await Future.delayed(const Duration(seconds: 1));
      List<${ListForm}Model> data = [];
      for (int i = 0; i < 10; i++) {
        data.add(${ListForm}Model(form1: "Form 1", form2: "Form 2"));
      }
      yield state.copyWith(
        status: const ${ListForm}StatusInitDone(),
        data: data,
      );
    } else if (event is ${ListForm}EventInputForm1) {
      yield state.copyWith(
        edForm1: event.value,
        status: const ${ListForm}StatusFillData(),
      );
    } else if (event is ${ListForm}EventSelectForm2) {
      yield state.copyWith(
        edForm2: event.value,
        status: ${ListForm}StatusFillData(
          forWhat: ['_form2Controller'],
          data: [event.value],
        ),
      );
    } else if (event is ${ListForm}EventSubmit) {
      try {
        yield state.copyWith(status: const ${ListForm}StatusLoading());
        await Future.delayed(const Duration(seconds: 1));
        yield state.copyWith(status: ${ListForm}StatusInfo("Info", "Success", 3));
      } on Error catch (e) {
        yield state.copyWith(status: ${ListForm}StatusInfo("Error", (e.toString()), 2));
      }
    } else if (event is ${ListForm}EventReset) {
      yield state.copyWith(
          edForm1: "",
          edForm2: "",
          status: const ${ListForm}StatusFillData(
            forWhat: ['_form1Controller', '_form2Controller'],
            data: ["", ""],
          ));
    }
  }
}

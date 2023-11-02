import 'package:flutter_bloc/flutter_bloc.dart';

abstract class ${CreateForm}Event {}

//EVENT-------------------------------------------------------------------------
class ${CreateForm}EventInit extends ${CreateForm}Event {}

class ${CreateForm}EventInputForm1 extends ${CreateForm}Event {
  final String value;

  ${CreateForm}EventInputForm1(this.value);
}

class ${CreateForm}EventSelectForm2 extends ${CreateForm}Event {
  final String value;

  ${CreateForm}EventSelectForm2(this.value);
}

class ${CreateForm}EventReset extends ${CreateForm}Event {}

class ${CreateForm}EventSubmit extends ${CreateForm}Event {}

//STATUS------------------------------------------------------------------------
abstract class ${CreateForm}Status {
  const ${CreateForm}Status();
}

class ${CreateForm}StatusInit extends ${CreateForm}Status {
  const ${CreateForm}StatusInit();
}

class ${CreateForm}StatusInitDone extends ${CreateForm}Status {
  const ${CreateForm}StatusInitDone();
}

class ${CreateForm}StatusFillData extends ${CreateForm}Status {
  final List<String> forWhat;
  final List<dynamic> data;

  const ${CreateForm}StatusFillData({
    this.forWhat = const [],
    this.data = const [],
  });
}

class ${CreateForm}StatusOnInput extends ${CreateForm}Status {
  const ${CreateForm}StatusOnInput();
}

class ${CreateForm}StatusLoading extends ${CreateForm}Status {
  const ${CreateForm}StatusLoading();
}

class ${CreateForm}StatusInfo extends ${CreateForm}Status {
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

  ${CreateForm}StatusInfo(this._title, this._msg, this._type);
}

//STATE--------------------------------------------------------------------------
class ${CreateForm}State {
  final String edForm1;

  final String edForm2;

  ${CreateForm}Model? data;
  final ${CreateForm}Status status;

  ${CreateForm}State({
    this.edForm1 = "",
    this.edForm2 = "",
    this.data,
    this.status = const ${CreateForm}StatusInit(),
  });

  ${CreateForm}State copyWith({
    String? edForm1,
    String? edForm2,
    ${CreateForm}Model? data,
    ${CreateForm}Status? status,
  }) {
    return ${CreateForm}State(
      edForm1: edForm1 ?? this.edForm1,
      edForm2: edForm2 ?? this.edForm2,
      data: data ?? this.data,
      status: status ?? this.status,
    );
  }
}

//BLOC--------------------------------------------------------------------------
class ${CreateForm}Bloc extends Bloc<${CreateForm}Event, ${CreateForm}State> {
  final ${CreateForm}Repo repo;
final String arguments;
  ${CreateForm}Bloc({
    required this.repo,
    required this.arguments,
  }) : super(${CreateForm}State());

  @override
  Stream<${CreateForm}State> mapEventToState(${CreateForm}Event event) async* {
    if (event is ${CreateForm}EventInit) {
      yield state.copyWith(status: const ${CreateForm}StatusLoading());
      await Future.delayed(const Duration(seconds: 1));
      ${CreateForm}Model data = ${CreateForm}Model(form1: "data 1", form2: "data 2");
      yield state.copyWith(status: const ${CreateForm}StatusInitDone(), data: data);
    } else if (event is ${CreateForm}EventInputForm1) {
      yield state.copyWith(
        edForm1: event.value,
        status: const ${CreateForm}StatusFillData(),
      );
    } else if (event is ${CreateForm}EventSelectForm2) {
      yield state.copyWith(
        edForm2: event.value,
        status: ${CreateForm}StatusFillData(
          forWhat: ['_form2Controller'],
          data: [event.value],
        ),
      );
    } else if (event is ${CreateForm}EventSubmit) {
      try {
        yield state.copyWith(status: const ${CreateForm}StatusLoading());
        await Future.delayed(const Duration(seconds: 1));
        yield state.copyWith(status: ${CreateForm}StatusInfo("Info", "Success", 3));
      } on Error catch (e) {
        yield state.copyWith(status: ${CreateForm}StatusInfo("Error", (e.toString()), 2));
      }
    } else if (event is ${CreateForm}EventReset) {
      yield state.copyWith(
          edForm1: "",
          edForm2: "",
          status: const ${CreateForm}StatusFillData(
            forWhat: ['_form1Controller', '_form2Controller'],
            data: ["", ""],
          ));
    }
  }
}
